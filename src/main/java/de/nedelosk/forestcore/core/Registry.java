package de.nedelosk.forestcore.core;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.fluids.FluidBlock;
import de.nedelosk.forestcore.items.FluidBucket;
import de.nedelosk.forestcore.modules.AModuleManager;
import de.nedelosk.forestcore.plugins.APluginManager;
import de.nedelosk.forestmods.common.events.BucketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public abstract class Registry {

	public final AModuleManager moduleManager = getModuleManager();
	public final APluginManager pluginManager = getPluginManager();
	public final IGuiHandler guiHandler = getGuiHandler();

	public void preInit(Object instance, FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		if (moduleManager != null) {
			moduleManager.registerModules();
			moduleManager.preInit();
		}
		if (pluginManager != null) {
			pluginManager.registerPlugins();
			pluginManager.preInit();
		}
	}

	public void init(Object instance, FMLInitializationEvent event) {
		if (moduleManager != null) {
			moduleManager.init();
		}
		if (pluginManager != null) {
			pluginManager.init();
		}
	}

	public void postInit(Object instance, FMLPostInitializationEvent event) {
		if (moduleManager != null) {
			moduleManager.postInit();
		}
		if (pluginManager != null) {
			pluginManager.postInit();
		}
	}

	public abstract AModuleManager getModuleManager();

	public abstract APluginManager getPluginManager();

	public abstract IGuiHandler getGuiHandler();

	public static Fluid registerFluid(String fluidName, int temperature, Material material, boolean createBucket, boolean isGas) {
		if (FluidRegistry.getFluid(fluidName) == null) {
			Fluid fluid = new Fluid(fluidName).setTemperature(temperature);
			if (material == Material.lava) {
				fluid.setLuminosity(12);
			}
			if (isGas) {
				fluid.isGaseous();
			}
			FluidRegistry.registerFluid(fluid);
			Block fluidBlock = new FluidBlock(fluid, material, fluidName);
			fluid.setUnlocalizedName(fluidName);
			GameRegistry.registerBlock(fluidBlock, "fluid_" + fluidName);
		}
		if (createBucket) {
			Item bucket = new FluidBucket(FluidRegistry.getFluid(fluidName).getBlock(), fluidName);
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(fluidName), new ItemStack(bucket), new ItemStack(Items.bucket));
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

	public static Block registerBlock(Block block, Class<? extends ItemBlock> itemblock, String name) {
		GameRegistry.registerBlock(block, itemblock, name);
		return block;
	}

	public static Block registerBlock(Block block, Class<? extends ItemBlock> itemblock, String name, Object... objects) {
		GameRegistry.registerBlock(block, itemblock, name, objects);
		return block;
	}

	public static void registerTile(Class<? extends TileEntity> tile, String name, String modName) {
		GameRegistry.registerTileEntity(tile, "forest." + modName + "." + name);
	}

	public static String setUnlocalizedBlockName(String name, String modName) {
		return "forest." + modName + ".block." + name;
	}

	public static Item registerItem(Item item, String name) {
		GameRegistry.registerItem(item, name);
		return item;
	}

	public static String setUnlocalizedItemName(String name, String modName) {
		return "forest." + modName + ".item." + name;
	}
}
