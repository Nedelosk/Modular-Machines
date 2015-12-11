package nedelosk.forestday.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.TabForestday;
import nedelosk.forestday.common.events.BucketHandler;
import nedelosk.forestday.common.fluids.FluidBlock;
import nedelosk.forestday.common.items.base.FluidBucket;
import nedelosk.forestday.common.modules.ModuleManager;
import nedelosk.forestday.plugins.PluginManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class FRegistry {

	PluginManager manangerPlugin = new PluginManager();
	ModuleManager managerModule = new ModuleManager();

	public void preInit() {
		CreativeTabs tabBlocks = Tabs.tabForestday = TabForestday.tabForestdayBlocks;

		managerModule.registerModules();
		managerModule.preInit();
		
		manangerPlugin.registerPlugins();
		manangerPlugin.preInitPlugins();
	}

	public void init() {
		managerModule.init();
		manangerPlugin.initPlugins();
	}

	public void postInit() {
		managerModule.postInit();
		manangerPlugin.postInitPlugins();
	}

	public static Fluid registerFluid(String fluidName, int temperature, Material material, boolean createBucket,
			boolean isGas) {
		if (FluidRegistry.getFluid(fluidName) == null) {
			Fluid fluid = new Fluid(fluidName).setTemperature(temperature);
			if (material == Material.lava)
				fluid.setLuminosity(12);
			if (isGas)
				fluid.isGaseous();
			FluidRegistry.registerFluid(fluid);
			Block fluidBlock = new FluidBlock(fluid, material, fluidName);
			fluid.setUnlocalizedName(fluidName);
			GameRegistry.registerBlock(fluidBlock, "fluid_" + fluidName);
		}
		if (createBucket) {
			Item bucket = new FluidBucket(FluidRegistry.getFluid(fluidName).getBlock(), fluidName);
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(fluidName), new ItemStack(bucket),
					new ItemStack(Items.bucket));
			BucketHandler.INSTANCE.buckets.put(FluidRegistry.getFluid(fluidName).getBlock(), bucket);
			GameRegistry.registerItem(bucket, "bucket_" + fluidName);
			return FluidRegistry.getFluid(fluidName);
		}
		return FluidRegistry.getFluid(fluidName);
	}

	public static Block registerBlock(Block block, String name, String modName) {
		GameRegistry.registerBlock(block, name);
		return block;
	}

	public static Block registerBlock(Block block, Class<? extends ItemBlock> itemblock, String name, String modName) {
		GameRegistry.registerBlock(block, itemblock, name);
		return block;
	}

	public static Block registerBlock(Block block, Class<? extends ItemBlock> itemblock, String name, String modName,
			Object... objects) {
		GameRegistry.registerBlock(block, itemblock, name, objects);
		return block;
	}

	public static void registerTile(Class<? extends TileEntity> tile, String name, String modName) {
		GameRegistry.registerTileEntity(tile, "forest." + modName + "." + name);
	}

	public static String setUnlocalizedBlockName(String name, String modName) {
		return "forest." + modName + ".block." + name;
	}

	public static Item registerItem(Item item, String name, String modName) {
		GameRegistry.registerItem(item, name);
		return item;
	}

	public static String setUnlocalizedItemName(String name, String modName) {
		return "forest." + modName + ".item." + name;
	}

}
