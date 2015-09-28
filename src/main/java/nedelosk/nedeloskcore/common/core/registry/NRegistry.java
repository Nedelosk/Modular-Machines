package nedelosk.nedeloskcore.common.core.registry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import minetweaker.MineTweakerAPI;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.common.blocks.fluid.FluidBlock;
import nedelosk.nedeloskcore.common.core.NedeloskCore;
import nedelosk.nedeloskcore.common.core.NedeloskCoreConfig;
import nedelosk.nedeloskcore.common.event.BucketHandler;
import nedelosk.nedeloskcore.common.items.FluidBucket;
import nedelosk.nedeloskcore.common.network.handler.PacketHandler;
import nedelosk.nedeloskcore.common.plan.PlanRecipeHandler;
import nedelosk.nedeloskcore.common.plan.PlanRecipeManager;
import nedelosk.nedeloskcore.common.world.WorldGeneratorNedeloskCore;
import nedelosk.nedeloskcore.plugins.NCPluginManager;
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

public class NRegistry {
	
	public static NRegistry instance;
	public static NCPluginManager pluginManager = new NCPluginManager();
	
	public static void preInit()
	{
		NedeloskCoreConfig.preInit();
		ObjectRegistry.preInit();
    	PacketHandler.preInit();
    	EntryRegistry.preInit();
    	
        NCoreApi.planRecipe = new PlanRecipeManager();
        
        if(Loader.isModLoaded("MineTweaker3"))
        {
        	MineTweakerAPI.registerClass(PlanRecipeHandler.class);
        }
        pluginManager.registerPlugins();
        pluginManager.preInit();
	}
	
	public static void init()
	{
		ObjectRegistry.init();
		NedeloskCore.proxy.init();
		pluginManager.init();
	}
	
	public static void postInit()
	{
		ObjectRegistry.postInit();
    	GameRegistry.registerWorldGenerator(new WorldGeneratorNedeloskCore(), 0);
    	pluginManager.postInit();
	}
	
	public static Fluid registerFluid(String fluidName, int temperature, Material material, boolean createBucket)
	{
		if(FluidRegistry.getFluid(fluidName) == null){
			Fluid fluid = new Fluid(fluidName).setTemperature(temperature);
			FluidRegistry.registerFluid(fluid);
			Block fluidBlock = new FluidBlock(fluid, material, fluidName);
			fluid.setUnlocalizedName(fluidName);
			GameRegistry.registerBlock(fluidBlock, "fluid_" + fluidName);
		}
		if(createBucket){
			Item bucket = new FluidBucket(FluidRegistry.getFluid(fluidName).getBlock(), fluidName);
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(fluidName), new ItemStack(bucket), new ItemStack(Items.bucket));
			BucketHandler.INSTANCE.buckets.put(FluidRegistry.getFluid(fluidName).getBlock(), bucket);
			GameRegistry.registerItem(bucket, "bucket_" + fluidName);
			return FluidRegistry.getFluid(fluidName);
		}
		return null;
	}

	public static Block registerBlock(Block block, String name, String modName) {
		GameRegistry.registerBlock(block, name);
		return block;
	}

	public static Block registerBlock(Block block, Class<? extends ItemBlock> itemblock, String name, String modName) {
		GameRegistry.registerBlock(block, itemblock, name);
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
