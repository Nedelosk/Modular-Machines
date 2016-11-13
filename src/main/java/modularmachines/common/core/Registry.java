package modularmachines.common.core;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPED;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.RecipeSorter;

import modularmachines.api.gui.IGuiProvider;
import modularmachines.api.modular.ModularManager;
import modularmachines.api.modules.models.ModuleModelLoader;
import modularmachines.client.model.ModelManager;
import modularmachines.client.model.ModelModular;
import modularmachines.common.config.Config;
import modularmachines.common.core.managers.AchievementManager;
import modularmachines.common.core.managers.BlockManager;
import modularmachines.common.core.managers.FluidManager;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.core.managers.ModuleManager;
import modularmachines.common.core.managers.OreDictionaryManager;
import modularmachines.common.fluids.FluidBlock;
import modularmachines.common.modular.ModularHelper;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.plugins.PluginManager;
import modularmachines.common.recipse.ModuleCrafterRecipe;

public class Registry implements IGuiHandler {

	public static final PluginManager PLUGIN_MANAGER = new PluginManager();
	public static Config config;

	private Registry() {
	}

	public static void preInit(FMLPreInitializationEvent event) {
		ModularManager.helper = new ModularHelper();
		config = new Config();
		Config.config = new Configuration(ModularMachines.configFile);
		Config.syncConfig(true);
		ModuleManager.registerCapability();
		new PacketHandler();
		FluidManager.registerFluids();
		BlockManager.registerBlocks();
		ItemManager.registerItems();
		BlockManager.registerTiles();
		NetworkRegistry.INSTANCE.registerGuiHandler(ModularMachines.instance, new Registry());
		PLUGIN_MANAGER.preInit();
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelManager.getInstance().registerModels();
			MinecraftForge.EVENT_BUS.register(ModelModular.class);
		}
		RecipeSorter.register("modularmachines:module_crafter", ModuleCrafterRecipe.class, SHAPED, "before:minecraft:shapeless");
		OreDictionaryManager.registerOres();
	}

	public static void init(FMLInitializationEvent event) {
		AchievementManager achManager = new AchievementManager();
		MinecraftForge.EVENT_BUS.register(achManager);
		ModuleManager.registerModuels();
		ModuleManager.registerModuleContainers();
		RecipeManager.registerRecipes();
		AchievementManager.registerPage();
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelManager.getInstance().registerItemAndBlockColors();
		}
		PLUGIN_MANAGER.init();
	}

	public static void postInit(FMLPostInitializationEvent event) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModuleModelLoader.loadModels();
		}
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
		Config.syncConfig(true);
		RecipeManager.registerHolderRecipes();
		PLUGIN_MANAGER.postInit();
	}

	public static Fluid registerFluid(String fluidName, int temperature, Material material, boolean createBlock, boolean isGas, int density) {
		Fluid fluid = FluidRegistry.getFluid(fluidName);
		if (fluid == null) {
			ResourceLocation stillLocation = new ResourceLocation("modularmachines:blocks/fluids/" + fluidName + "_still");
			fluid = new Fluid(fluidName, stillLocation, stillLocation).setTemperature(temperature);
			fluid.setUnlocalizedName(fluidName);
			if (isGas) {
				fluid.setGaseous(isGas);
			}
			fluid.setDensity(density);
			FluidRegistry.registerFluid(fluid);
		}
		if (createBlock) {
			if (!fluid.canBePlacedInWorld()) {
				Block fluidBlock = new FluidBlock(fluid, material, fluidName);
				fluidBlock.setRegistryName("fluid_" + fluidName);
				GameRegistry.register(fluidBlock);
				ItemBlock itemBlock = new ItemBlock(fluidBlock);
				itemBlock.setRegistryName("fluid_" + fluidName);
				GameRegistry.register(itemBlock);
				ModularMachines.proxy.registerFluidStateMapper(fluidBlock, fluid);
			}
			if (!FluidRegistry.getBucketFluids().contains(fluid)) {
				FluidRegistry.addBucketForFluid(fluid);
			}
		}
		return FluidRegistry.getFluid(fluidName);
	}

	public static <V extends IForgeRegistryEntry<V>> IForgeRegistryEntry<V> register(V entry, String name) {
		entry.setRegistryName(new ResourceLocation(Loader.instance().activeModContainer().getModId(), name));
		GameRegistry.register(entry);
		if (entry instanceof Block) {
			ModularMachines.proxy.registerBlock((Block) entry);
		} else if (entry instanceof Item) {
			ModularMachines.proxy.registerItem((Item) entry);
		}
		return entry;
	}

	public static Item register(Item entry) {
		entry.setRegistryName(new ResourceLocation(Loader.instance().activeModContainer().getModId(), entry.getUnlocalizedName()));
		GameRegistry.register(entry);
		ModularMachines.proxy.registerItem(entry);
		return entry;
	}

	public static void registerTile(Class<? extends TileEntity> tile, String name, String modName) {
		GameRegistry.registerTileEntity(tile, "modularmachines." + modName + "." + name);
	}

	public static String setUnlocalizedBlockName(String name) {
		return "modularmachines.block." + name;
	}

	public static String setUnlocalizedItemName(String name) {
		return "modularmachines.item." + name;
	}

	/* GUI HANDLER */
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
			case 0:
				if (tile != null && tile instanceof IGuiProvider) {
					return ((IGuiProvider) tile).createContainer(player.inventory);
				}
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if ((world instanceof WorldClient)) {
			switch (ID) {
				case 0:
					if (tile instanceof IGuiProvider) {
						return ((IGuiProvider) tile).createGui(player.inventory);
					}
				default:
					return null;
			}
		}
		return null;
	}
}
