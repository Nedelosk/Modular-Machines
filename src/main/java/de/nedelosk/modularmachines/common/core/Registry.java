package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.common.fluids.FluidBlock;
import de.nedelosk.modularmachines.common.plugins.PluginManager;
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

	public final PluginManager pluginManager = getPluginManager();
	public final IGuiHandler guiHandler = getGuiHandler();

	public void preInit(Object instance, FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		if (pluginManager != null) {
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

	public abstract PluginManager getPluginManager();

	public abstract IGuiHandler getGuiHandler();

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
			if(!fluid.canBePlacedInWorld()){
				Block fluidBlock = new FluidBlock(fluid, material, fluidName);
				fluidBlock.setRegistryName("fluid_" + fluidName);
				GameRegistry.register(fluidBlock);
				ItemBlock itemBlock = new ItemBlock(fluidBlock);
				itemBlock.setRegistryName("fluid_" + fluidName);
				GameRegistry.register(itemBlock);
				ModularMachines.proxy.registerFluidStateMapper(fluidBlock, fluid);
			}
			if(!FluidRegistry.getBucketFluids().contains(fluid)){
				FluidRegistry.addBucketForFluid(fluid);
			}
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
}
