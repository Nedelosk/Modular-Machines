package nedelosk.nedeloskcore.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.nedeloskcore.api.Material;
import nedelosk.nedeloskcore.api.Material.MaterialType;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.common.blocks.BlockMultiblock;
import nedelosk.nedeloskcore.common.blocks.BlockMultiblockValve;
import nedelosk.nedeloskcore.common.blocks.BlockOre;
import nedelosk.nedeloskcore.common.blocks.BlockPlan;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockValve;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.items.ItemBlockForest;
import nedelosk.nedeloskcore.common.items.ItemGearWood;
import nedelosk.nedeloskcore.common.items.ItemGem;
import nedelosk.nedeloskcore.common.items.ItemIngot;
import nedelosk.nedeloskcore.common.items.ItemNugget;
import nedelosk.nedeloskcore.common.items.ItemPlan;
import nedelosk.nedeloskcore.common.items.ItemWoodBucket;
import nedelosk.nedeloskcore.common.items.blocks.ItemBlockMultiblock;
import nedelosk.nedeloskcore.common.items.blocks.ItemBlockPlan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import nedelosk.nedeloskcore.common.core.registry.NCRegistry;

public class ObjectRegistry {
	
	public static Material WOOD;
	
	public static void preInit()
	{
		WOOD = NCoreApi.registerMaterial(new Material("wood", 600, 1.5F, 1F, MaterialType.WOOD, "woodOak", new ItemStack(Blocks.log, 1, 0)));
		NCoreApi.registerMaterial(new Material("clayBrick", 1400, 1.5F, 1F, MaterialType.BRICK, "ingotBrick", new ItemStack(Blocks.brick_block, 1, 0)));
		NCoreApi.registerMaterial(new Material("stoneBrick", 1600, 3F, 1F, MaterialType.BRICK, "bricksStone", new ItemStack(Blocks.stonebrick, 1, 0)));
		NCoreApi.registerMaterial(new Material("netherBrick", 2000, 5F, 1F, MaterialType.BRICK, "ingotBrickNether", new ItemStack(Blocks.nether_brick, 1, 0)));
		NCoreApi.registerMaterial(new Material("metalIron", 1400, 4.5F, 2F, MaterialType.METAL, "plateIron", new ItemStack(Blocks.iron_block, 1, 0)));
		//NCoreApi.registerMaterial(new Material("metalSteel", 2400, 7F, 3F, MaterialType.METAL, "plateSteel"));
		GameRegistry.registerTileEntity(TileMultiblockBase.class, "tile.multiblock.base");
		GameRegistry.registerTileEntity(TileMultiblockValve.class, "tile.multiblock.fluid");
		
		NCBlockManager.Multiblock.registerBlock(new BlockMultiblock(), ItemBlockMultiblock.class);
		NCBlockManager.Multiblock_Valve.registerBlock(new BlockMultiblockValve(), ItemBlockMultiblock.class);
		
		NCItemManager.Plan.registerItem(new ItemPlan());
		NCBlockManager.Plan_Block.registerBlock(new BlockPlan(), ItemBlockPlan.class);
		NCRegistry.registerTile(TilePlan.class, "plan", "nc");
		
		NCItemManager.Bucket_Wood.registerItem(new ItemWoodBucket(Blocks.air, "bucket.wood"));
		NCItemManager.Bucket_Wood_Water.registerItem(new ItemWoodBucket(Blocks.water, "bucket.wood.water"));
		
		NCBlockManager.Ore.registerBlock(new BlockOre(BlockOre.ores, "nedeloskcore"), ItemBlockForest.class);
		NCItemManager.Ingots.registerItem(new ItemIngot(ItemIngot.ingots, "nedeloskcore"));
		NCItemManager.Nuggets.registerItem(new ItemNugget(ItemNugget.nuggets, "nedeloskcore"));
		NCItemManager.Gems.registerItem(new ItemGem());
		NCItemManager.Gears_Wood.registerItem(new ItemGearWood());
	}
	
	public static void init()
	{
		registerOreDict();
	}
	
	public static void postInit()
	{
		registerRecipes();
	}
	
	public static void registerRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(NCItemManager.Plan.item(), 1, 1), Items.paper);
		GameRegistry.addShapelessRecipe(new ItemStack(NCItemManager.Plan.item(), 1, 2), new ItemStack(NCItemManager.Plan.item(), 1, 1), Items.stick, Items.stick);
		GameRegistry.addShapedRecipe(new ItemStack(NCItemManager.Bucket_Wood.item()), "   ", "+ +", " + ", '+', Blocks.planks);
		GameRegistry.addSmelting(new ItemStack(NCBlockManager.Ore.item(), 1, 0), new ItemStack(NCItemManager.Ingots.item(), 1, 0),  0.5F);
		GameRegistry.addSmelting(new ItemStack(NCBlockManager.Ore.item(), 1, 1), new ItemStack(NCItemManager.Ingots.item(), 1, 1),  0.5F);
		GameRegistry.addSmelting(new ItemStack(NCBlockManager.Ore.item(), 1, 2), new ItemStack(NCItemManager.Ingots.item(), 1, 2),  0.5F);
		GameRegistry.addSmelting(new ItemStack(NCBlockManager.Ore.item(), 1, 3), new ItemStack(NCItemManager.Ingots.item(), 1, 3),  0.5F);
		GameRegistry.addSmelting(new ItemStack(NCBlockManager.Ore.item(), 1, 4), new ItemStack(NCItemManager.Ingots.item(), 1, 4),  0.5F);
		GameRegistry.addShapedRecipe(new ItemStack(NCItemManager.Ingots.item(), 1, 0), "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Nuggets.item(),1 , 0));
		GameRegistry.addShapedRecipe(new ItemStack(NCItemManager.Ingots.item(), 1, 1), "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Nuggets.item(),1 , 1));
		GameRegistry.addShapedRecipe(new ItemStack(NCItemManager.Ingots.item(), 1, 2), "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Nuggets.item(),1 , 2));
		GameRegistry.addShapedRecipe(new ItemStack(NCItemManager.Ingots.item(), 1, 3), "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Nuggets.item(),1 , 3));
		GameRegistry.addShapedRecipe(new ItemStack(NCItemManager.Ingots.item(), 1, 4), "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Nuggets.item(),1 , 4));
		GameRegistry.addShapelessRecipe(new ItemStack(NCItemManager.Nuggets.item(), 9, 0), new ItemStack(NCItemManager.Ingots.item(), 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(NCItemManager.Nuggets.item(), 9, 1), new ItemStack(NCItemManager.Ingots.item(), 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(NCItemManager.Nuggets.item(), 9, 2), new ItemStack(NCItemManager.Ingots.item(), 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(NCItemManager.Nuggets.item(), 9, 3), new ItemStack(NCItemManager.Ingots.item(), 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(NCItemManager.Nuggets.item(), 9, 4), new ItemStack(NCItemManager.Ingots.item(), 1, 4));
		
		for(Material material : NCoreApi.getMaterials())
		{
			if(material.type == MaterialType.BRICK)
			{
				addShapedRecipe(new ItemStack(NCBlockManager.Multiblock.item(), 4, NCoreApi.getMaterials().indexOf(material)), "+++", "+-+", "+++", '+', material.oreDict, '-', Items.clay_ball);
				addShapedRecipe(new ItemStack(NCBlockManager.Multiblock_Valve.item(), 2, NCoreApi.getMaterials().indexOf(material)), "+I+", "I-I", "+I+", '+', material.oreDict, '-', Blocks.lever, 'I', Blocks.iron_bars);
			}
			else if(material.type == MaterialType.METAL)
			{
				addShapedRecipe(new ItemStack(NCBlockManager.Multiblock.item(), 4, NCoreApi.getMaterials().indexOf(material)), "+++", "+-+", "+++", '+', material.oreDict, '-', Items.redstone);
				addShapedRecipe(new ItemStack(NCBlockManager.Multiblock_Valve.item(), 2, NCoreApi.getMaterials().indexOf(material)), "+I+", "I-I", "+I+", '+', material.oreDict, '-', Blocks.lever, 'I', Blocks.iron_bars);
			}
			else if(material.type == MaterialType.WOOD)
			{
				addShapedRecipe(new ItemStack(NCBlockManager.Multiblock.item(), 4, NCoreApi.getMaterials().indexOf(material)), "+++", "+-+", "+++", '+', material.oreDict, '-', Items.stick);
				addShapedRecipe(new ItemStack(NCBlockManager.Multiblock_Valve.item(), 2, NCoreApi.getMaterials().indexOf(material)), "+I+", "I-I", "+I+", '+', material.oreDict, '-', Blocks.lever, 'I', Blocks.ladder);
			}
		}
	}
	
	public static void registerOreDict()
	{
		OreDictionary.registerOre("ingotCopper", new ItemStack(NCItemManager.Ingots.item(), 1, 0));
		OreDictionary.registerOre("ingotTin", new ItemStack(NCItemManager.Ingots.item(), 1, 1));
		OreDictionary.registerOre("ingotSilver", new ItemStack(NCItemManager.Ingots.item(), 1, 2));
		OreDictionary.registerOre("ingotLead", new ItemStack(NCItemManager.Ingots.item(), 1, 3));
		OreDictionary.registerOre("ingotNickel", new ItemStack(NCItemManager.Ingots.item(), 1, 4));
		OreDictionary.registerOre("nuggetCopper", new ItemStack(NCItemManager.Nuggets.item(), 1, 0));
		OreDictionary.registerOre("nuggetTin", new ItemStack(NCItemManager.Nuggets.item(), 1, 1));
		OreDictionary.registerOre("nuggetSilver", new ItemStack(NCItemManager.Nuggets.item(), 1, 2));
		OreDictionary.registerOre("nuggetLead", new ItemStack(NCItemManager.Nuggets.item(), 1, 3));
		OreDictionary.registerOre("nuggetNickel", new ItemStack(NCItemManager.Nuggets.item(), 1, 4));
		OreDictionary.registerOre("nuggetIron", new ItemStack(NCItemManager.Nuggets.item(), 1, 5));
		OreDictionary.registerOre("oreCopper", new ItemStack(NCBlockManager.Ore.item(), 1, 0));
		OreDictionary.registerOre("oreTin", new ItemStack(NCBlockManager.Ore.item(), 1, 1));
		OreDictionary.registerOre("oreSilver", new ItemStack(NCBlockManager.Ore.item(), 1, 2));
		OreDictionary.registerOre("oreLead", new ItemStack(NCBlockManager.Ore.item(), 1, 3));
		OreDictionary.registerOre("oreNickel", new ItemStack(NCBlockManager.Ore.item(), 1, 4));
		OreDictionary.registerOre("oreRuby", new ItemStack(NCBlockManager.Ore.item(), 1, 5));
		OreDictionary.registerOre("gemRuby", new ItemStack(NCItemManager.Gems.item(), 1, 0));
		OreDictionary.registerOre("bricksStone", Blocks.stonebrick);
	}
	
	public static void addShapedRecipe(ItemStack stack, Object... obj)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(stack, obj));
	}
	
	public static void addShapelessRecipe(ItemStack stack, Object... obj)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(stack, obj));
	}
	
}
