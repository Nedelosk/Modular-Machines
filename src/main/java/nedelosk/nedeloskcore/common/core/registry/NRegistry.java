package nedelosk.nedeloskcore.common.core.registry;

import java.util.HashMap;

import minetweaker.MineTweakerAPI;
import nedelosk.forestday.common.registrys.FRegistry;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.common.blocks.BlockPlan;
import nedelosk.nedeloskcore.common.blocks.fluid.FluidBlock;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.common.event.BucketHandler;
import nedelosk.nedeloskcore.common.items.FluidBucket;
import nedelosk.nedeloskcore.common.items.ItemPlan;
import nedelosk.nedeloskcore.common.items.ItemWoodBucket;
import nedelosk.nedeloskcore.common.items.blocks.ItemBlockPlan;
import nedelosk.nedeloskcore.common.network.handler.PacketHandler;
import nedelosk.nedeloskcore.common.plan.PlanRecipeHandler;
import nedelosk.nedeloskcore.common.plan.PlanRecipeManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class NRegistry {
	
	public static NRegistry instance;
	public static Item plan;
	public static Block planBlock;
	public static Item woodBucket;
	public static Item woodBucketWater;
	
	public static void preInit()
	{
		plan = registerItem(new ItemPlan(), "plan", "nc");
		planBlock = registerBlock(new BlockPlan(), ItemBlockPlan.class, "plan", "nc");
		registerTile(TilePlan.class, "plan", "nc");
		
		woodBucket = registerItem(new ItemWoodBucket(Blocks.air, "bucket.wood"), "bucket.wood", "nc");
		woodBucketWater = registerItem(new ItemWoodBucket(Blocks.water, "bucket.wood.water"), "bucket.wood.water", "nc");
		
		GameRegistry.addShapelessRecipe(new ItemStack(plan, 1, 1), Items.paper);
		GameRegistry.addShapelessRecipe(new ItemStack(plan, 1, 2), new ItemStack(plan, 1, 1), Items.stick, Items.stick);
		GameRegistry.addShapedRecipe(new ItemStack(woodBucket), "   ", "+ +", " + ", '+', Blocks.planks);
		BookDatas.readManuals();
    	PacketHandler.preInit();
    	EntryRegistry.preInit();
    	
        NCoreApi.planRecipe = new PlanRecipeManager();
        
        if(Loader.isModLoaded("MineTweaker3"))
        {
        	MineTweakerAPI.registerClass(PlanRecipeHandler.class);
        }
	}
	
	public static void init()
	{
		BookDatas.readManuals();
	}
	
	public static void postInit()
	{
		
	}
	
	public static Fluid registerFluid(String fluidName, int temperature, Material material, boolean createBucket)
	{
		if(FluidRegistry.getFluid(fluidName) == null)
		{
		Fluid fluid = new Fluid(fluidName).setTemperature(temperature);
		FluidRegistry.registerFluid(fluid);
		Block fluidBlock = new FluidBlock(fluid, material, fluidName);
		fluid.setUnlocalizedName(fluidName);
		GameRegistry.registerBlock(fluidBlock, "fluid_" + fluidName);
		}
		if(createBucket)
		{
		Item bucket = new FluidBucket(FluidRegistry.getFluid(fluidName).getBlock(), fluidName);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(fluidName), new ItemStack(bucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(FluidRegistry.getFluid(fluidName).getBlock(), bucket);
		GameRegistry.registerItem(bucket, "bucket_" + fluidName);
		return FluidRegistry.getFluid(fluidName);
		}
		return null;
	}

	public static Block registerBlock(Block block, String name, String modName) {
		GameRegistry.registerBlock(block, setUnlocalizedBlockName(name, modName));
		return block;
	}

	public static Block registerBlock(Block block, Class<? extends ItemBlock> itemblock, String name, String modName) {
		GameRegistry.registerBlock(block, itemblock, setUnlocalizedBlockName(name, modName));
		return block;
	}

	public static void registerTile(Class<? extends TileEntity> tile, String name, String modName) {
		GameRegistry.registerTileEntity(tile, "forest." + modName + "." + name);
	}

	public static String setUnlocalizedBlockName(String name, String modName) {
		return "forest." + modName + ".block." + name;
	}

	public static Item registerItem(Item item, String name, String modName) {
		GameRegistry.registerItem(item, setUnlocalizedItemName(name, modName));
		return item;
	}

	public static String setUnlocalizedItemName(String name, String modName) {
		return "forest." + modName + ".item." + name;
	}
	
}
