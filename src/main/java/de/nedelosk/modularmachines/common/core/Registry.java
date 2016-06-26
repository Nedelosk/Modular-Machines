package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.common.fluids.FluidBlock;
import de.nedelosk.modularmachines.common.plugins.APluginManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public abstract class Registry {

	public final APluginManager pluginManager = getPluginManager();
	public final IGuiHandler guiHandler = getGuiHandler();

	public void preInit(Object instance, FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		if (pluginManager != null) {
			pluginManager.registerPlugins();
			pluginManager.preInit();
		}
	}

	public void init(Object instance, FMLInitializationEvent event) {
		if (pluginManager != null) {
			pluginManager.init();
		}
	}

	public void postInit(Object instance, FMLPostInitializationEvent event) {
		if (pluginManager != null) {
			pluginManager.postInit();
		}
	}

	public abstract APluginManager getPluginManager();

	public abstract IGuiHandler getGuiHandler();

	public static Fluid registerFluid(String fluidName, int temperature, Material material, boolean createBucket, boolean isGas) {
		if (FluidRegistry.getFluid(fluidName) == null) {
			Fluid fluid = new Fluid(fluidName, new ResourceLocation("modularmachines:textures/blocks/fluids" + fluidName + "_still.png"), new ResourceLocation("modularmachines:textures/blocks/fluids" + fluidName + "_still.png")).setTemperature(temperature);
			fluid.setUnlocalizedName(fluidName);
			if (material == Material.LAVA) {
				fluid.setLuminosity(12);
			}
			if (isGas) {
				fluid.isGaseous();
			}
			FluidRegistry.registerFluid(fluid);
			Block fluidBlock = new FluidBlock(fluid, material, fluidName);
			fluidBlock.setRegistryName("fluid_" + fluidName);
			GameRegistry.register(fluidBlock);
			ItemBlock itemBlock = new ItemBlock(fluidBlock);
			itemBlock.setRegistryName("fluid_" + fluidName);
			GameRegistry.register(itemBlock);
			ModularMachines.proxy.registerFluidStateMapper(fluidBlock, fluid);
		}
		if (createBucket) {
			FluidRegistry.addBucketForFluid(FluidRegistry.getFluid(fluidName));
		}
		return FluidRegistry.getFluid(fluidName);
	}

	public static <V extends IForgeRegistryEntry<V>> IForgeRegistryEntry<V> register(V entry, String name) {
		entry.setRegistryName(new ResourceLocation(Loader.instance().activeModContainer().getModId(), name));
		GameRegistry.register(entry);
		if(entry instanceof Block){
			ModularMachines.proxy.registerBlock((Block) entry);
		}else if(entry instanceof Item){
			ModularMachines.proxy.registerItem((Item)entry);
		}
		return entry;
	}

	public static void registerTile(Class<? extends TileEntity> tile, String name, String modName) {
		GameRegistry.registerTileEntity(tile, "forest." + modName + "." + name);
	}

	public static String setUnlocalizedBlockName(String name) {
		return "forest.block." + name;
	}

	public static String setUnlocalizedItemName(String name) {
		return "forest.item." + name;
	}
}
