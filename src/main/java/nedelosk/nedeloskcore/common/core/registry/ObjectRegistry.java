package nedelosk.nedeloskcore.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.nedeloskcore.common.blocks.BlockOre;
import nedelosk.nedeloskcore.common.blocks.BlockPlan;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.items.ItemBlockForest;
import nedelosk.nedeloskcore.common.items.ItemGearWood;
import nedelosk.nedeloskcore.common.items.ItemGem;
import nedelosk.nedeloskcore.common.items.ItemIngot;
import nedelosk.nedeloskcore.common.items.ItemNugget;
import nedelosk.nedeloskcore.common.items.ItemPlan;
import nedelosk.nedeloskcore.common.items.ItemWoodBucket;
import nedelosk.nedeloskcore.common.items.blocks.ItemBlockPlan;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;

public class ObjectRegistry {

	public static Item plan;
	public static Block planBlock;
	public static Item woodBucket;
	public static Item woodBucketWater;
	public static Block ore;
	public static Item ingots;
	public static Item nuggets;
	public static Item gems;
	public static Item gearWood;
	
	public static void preInit()
	{
		plan = NRegistry.registerItem(new ItemPlan(), "plan", "nc");
		planBlock = NRegistry.registerBlock(new BlockPlan(), ItemBlockPlan.class, "plan", "nc");
		NRegistry.registerTile(TilePlan.class, "plan", "nc");
		
		woodBucket = NRegistry.registerItem(new ItemWoodBucket(Blocks.air, "bucket.wood"), "bucket.wood", "nc");
		woodBucketWater = NRegistry.registerItem(new ItemWoodBucket(Blocks.water, "bucket.wood.water"), "bucket.wood.water", "nc");
		
		ore = NRegistry.registerBlock(new BlockOre(BlockOre.ores, "nedeloskcore"), ItemBlockForest.class, "ore", "nc");
		ingots = NRegistry.registerItem(new ItemIngot(ItemIngot.ingots, "nedeloskcore"), "ingot", "nc");
		nuggets = NRegistry.registerItem(new ItemNugget(ItemNugget.nuggets, "nedeloskcore"), "nugget", "nc");
		gems = NRegistry.registerItem(new ItemGem(), "gem", "nc");
		gearWood = NRegistry.registerItem(new ItemGearWood(), "gearWood", "nc");
	}
	
	public static void init()
	{
		registerRecipes();
		registerOreDict();
	}
	
	public static void registerRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(plan, 1, 1), Items.paper);
		GameRegistry.addShapelessRecipe(new ItemStack(plan, 1, 2), new ItemStack(plan, 1, 1), Items.stick, Items.stick);
		GameRegistry.addShapedRecipe(new ItemStack(woodBucket), "   ", "+ +", " + ", '+', Blocks.planks);
		GameRegistry.addSmelting(new ItemStack(ore, 1, 0), new ItemStack(ingots, 1, 0),  0.5F);
		GameRegistry.addSmelting(new ItemStack(ore, 1, 1), new ItemStack(ingots, 1, 1),  0.5F);
		GameRegistry.addSmelting(new ItemStack(ore, 1, 2), new ItemStack(ingots, 1, 2),  0.5F);
		GameRegistry.addSmelting(new ItemStack(ore, 1, 3), new ItemStack(ingots, 1, 3),  0.5F);
		GameRegistry.addSmelting(new ItemStack(ore, 1, 4), new ItemStack(ingots, 1, 4),  0.5F);
		GameRegistry.addShapedRecipe(new ItemStack(ingots, 1, 0), "+++", "+++", "+++", '+', new ItemStack(nuggets,1 , 0));
		GameRegistry.addShapedRecipe(new ItemStack(ingots, 1, 1), "+++", "+++", "+++", '+', new ItemStack(nuggets,1 , 1));
		GameRegistry.addShapedRecipe(new ItemStack(ingots, 1, 2), "+++", "+++", "+++", '+', new ItemStack(nuggets,1 , 2));
		GameRegistry.addShapedRecipe(new ItemStack(ingots, 1, 3), "+++", "+++", "+++", '+', new ItemStack(nuggets,1 , 3));
		GameRegistry.addShapedRecipe(new ItemStack(ingots, 1, 4), "+++", "+++", "+++", '+', new ItemStack(nuggets,1 , 4));
		GameRegistry.addShapelessRecipe(new ItemStack(nuggets,9 , 0), new ItemStack(ingots, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(nuggets,9 , 1), new ItemStack(ingots, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(nuggets,9 , 2), new ItemStack(ingots, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(nuggets,9 , 3), new ItemStack(ingots, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(nuggets,9 , 4), new ItemStack(ingots, 1, 4));
	}
	
	public static void registerOreDict()
	{
		OreDictionary.registerOre("ingotCopper", new ItemStack(ingots, 1, 0));
		OreDictionary.registerOre("ingotTin", new ItemStack(ingots, 1, 1));
		OreDictionary.registerOre("ingotSilver", new ItemStack(ingots, 1, 2));
		OreDictionary.registerOre("ingotLead", new ItemStack(ingots, 1, 3));
		OreDictionary.registerOre("ingotNickel", new ItemStack(ingots, 1, 4));
		OreDictionary.registerOre("nuggetCopper", new ItemStack(nuggets, 1, 0));
		OreDictionary.registerOre("nuggetTin", new ItemStack(nuggets, 1, 1));
		OreDictionary.registerOre("nuggetSilver", new ItemStack(nuggets, 1, 2));
		OreDictionary.registerOre("nuggetLead", new ItemStack(nuggets, 1, 3));
		OreDictionary.registerOre("nuggetNickel", new ItemStack(nuggets, 1, 4));
		OreDictionary.registerOre("nuggetIron", new ItemStack(nuggets, 1, 5));
		OreDictionary.registerOre("oreCopper", new ItemStack(ore, 1, 0));
		OreDictionary.registerOre("oreTin", new ItemStack(ore, 1, 1));
		OreDictionary.registerOre("oreSilver", new ItemStack(ore, 1, 2));
		OreDictionary.registerOre("oreLead", new ItemStack(ore, 1, 3));
		OreDictionary.registerOre("oreNickel", new ItemStack(ore, 1, 4));
		OreDictionary.registerOre("oreRuby", new ItemStack(ore, 1, 5));
		OreDictionary.registerOre("gemRuby", new ItemStack(gems, 1, 0));
	}
	
}
