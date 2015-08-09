package nedelosk.forestday.common.managers;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.api.crafting.BurningMode;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.IBurnRecipe;
import nedelosk.forestday.api.crafting.ICampfireRecipe;
import nedelosk.forestday.api.crafting.ICokeFurnaceRecipe;
import nedelosk.forestday.api.crafting.IKilnRecipe;
import nedelosk.forestday.api.crafting.IWoodTypeManager;
import nedelosk.forestday.api.crafting.IWorkbenchRecipe;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.machines.base.furnace.coke.CokeFurnaceRecipeManager;
import nedelosk.forestday.common.machines.base.heater.generator.HeatGeneratorRecipeManager;
import nedelosk.forestday.common.machines.base.wood.campfire.CampfireRecipeManager;
import nedelosk.forestday.common.machines.base.wood.kiln.KilnRecipeManager;
import nedelosk.forestday.common.machines.base.wood.workbench.WorkbenchRecipeManager;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.WoodTypeManager;
import nedelosk.forestday.common.registrys.FBlocks;
import nedelosk.forestday.common.registrys.FItems;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.crafting.IPlanRecipe;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import nedelosk.nedeloskcore.common.core.registry.ObjectRegistry;
import nedelosk.nedeloskcore.utils.CraftingHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingManager {
	
	public static IKilnRecipe kilnResin;
	public static IBurnRecipe burning;
	public static ICokeFurnaceRecipe furnaceCoke;
	public static IWorkbenchRecipe workbench;
	public static ICampfireRecipe campfire;
	public static IPlanRecipe planRecipe;
	public static IWoodTypeManager woodManager;
	
	public static void removeRecipes()
	{
		CraftingHelper.removeAnyRecipe(new ItemStack(Items.bowl, 4));
		CraftingHelper.removeFurnaceRecipe(Items.brick);
	}
	
	public static void registerRecipes()
	{
		ForestdayCrafting.kilnResinRecipes = new KilnRecipeManager();
		ForestdayCrafting.burningRecipe = new HeatGeneratorRecipeManager();
		ForestdayCrafting.furnaceCokeRecipe = new CokeFurnaceRecipeManager();
		ForestdayCrafting.workbenchRecipe = new WorkbenchRecipeManager();
		ForestdayCrafting.campfireRecipe = new CampfireRecipeManager();
		ForestdayCrafting.woodManager = new WoodTypeManager();
		
		kilnResin = ForestdayCrafting.kilnResinRecipes;
		burning = ForestdayCrafting.burningRecipe;
		furnaceCoke = ForestdayCrafting.furnaceCokeRecipe;
		workbench = ForestdayCrafting.workbenchRecipe;
		campfire = ForestdayCrafting.campfireRecipe;
		planRecipe = NCoreApi.planRecipe;
		woodManager = ForestdayCrafting.woodManager;
		
		addMachineRecipes();
		addCampfireRecipes();
		addWorkbenchRecipes();
		addNormalRecipes();
		
		addWoodRecipes();
		addFuelToGenerator();
	}
	
	public static void addNormalRecipes()
	{
		addShapelessRecipe(new ItemStack(FBlocks.Gravel.item()), Blocks.dirt, Blocks.dirt, Blocks.dirt, Blocks.dirt, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.sand);
		
		addShapelessRecipe(new ItemStack(FItems.axe_flint.item()), Items.stick, Items.stick, Items.flint, Items.flint);
		
		addShapelessRecipe(new ItemStack(FItems.tool_parts.item()), Items.stick, Items.leather);
		addShapelessRecipe(new ItemStack(FItems.tool_parts.item(), 1, 1), new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(Blocks.stone_slab, 1, 3), Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(FItems.tool_parts.item(), 1, 2), Items.iron_ingot, Items.iron_ingot, Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(FItems.tool_parts.item(), 1, 3), Items.diamond, Items.diamond);
		addShapelessRecipe(new ItemStack(FItems.tool_parts.item(), 1, 4), Items.stick, Items.stick, Items.stick, Items.leather);
		addShapelessRecipe(new ItemStack(FItems.tool_parts.item(), 1, 5), new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(Blocks.stone_slab, 1, 3), Items.flint);
		addShapelessRecipe(new ItemStack(FItems.tool_parts.item(), 1, 6), Items.iron_ingot, Items.iron_ingot, Items.flint);
		addShapelessRecipe(new ItemStack(FItems.tool_parts.item(), 1, 7), Items.stick, Items.stick);
		addShapedRecipe(new ItemStack(FItems.tool_parts.item(), 1, 8), "   ", "+++", "  +", '+', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(FItems.tool_parts.item(), 1, 9), "+++", "  +", "  +", '+', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(FItems.tool_parts.item(), 1, 11), "  +", " + ", "+  ", '+', "plankWood");
		addShapedRecipe(new ItemStack(FItems.tool_parts.item(), 1, 10), "  +", " + ", "   ", '+', "plankWood");
		addShapedRecipe(new ItemStack(FItems.tool_parts.item(), 1, 10), "   ", " + ", "+  ", '+', "plankWood");
		
		addShapelessRecipe(new ItemStack(FItems.file_stone.item()), new ItemStack(FItems.tool_parts.item(), 1, 0), new ItemStack(FItems.tool_parts.item(), 1, 1));
		addShapelessRecipe(new ItemStack(FItems.file_iron.item()), new ItemStack(FItems.tool_parts.item(), 1, 0), new ItemStack(FItems.tool_parts.item(), 1, 2));
		addShapelessRecipe(new ItemStack(FItems.file_diamond.item()), new ItemStack(FItems.tool_parts.item(), 1, 0), new ItemStack(FItems.tool_parts.item(), 1, 3));
		addShapelessRecipe(new ItemStack(FItems.knife_stone.item()), new ItemStack(FItems.tool_parts.item(), 1, 4), new ItemStack(FItems.tool_parts.item(), 1, 5));
		addShapelessRecipe(new ItemStack(FItems.cutter.item()), new ItemStack(FItems.tool_parts.item(), 1, 6), new ItemStack(FItems.tool_parts.item(), 1, 7));
		addShapelessRecipe(new ItemStack(FItems.adze.item()), new ItemStack(FItems.tool_parts.item(), 1, 8), new ItemStack(FItems.tool_parts.item(), 1, 10));
		addShapelessRecipe(new ItemStack(FItems.adze_long.item()), new ItemStack(FItems.tool_parts.item(), 1, 9), new ItemStack(FItems.tool_parts.item(), 1, 11));
		addShapelessRecipe(new ItemStack(FItems.bow_and_stick.item()), Items.bow, Items.stick, Items.stick);
		
		addShapelessRecipe(new ItemStack(FItems.nature.item(), 1, 8), Blocks.sand, Blocks.sand, Blocks.gravel, Blocks.gravel, Items.water_bucket);
		addShapelessRecipe(new ItemStack(FItems.nature.item(), 1, 8), Blocks.sand, Blocks.sand, Blocks.gravel, Blocks.gravel, ObjectRegistry.woodBucketWater);
	}
	
	public static void addWoodRecipes()
	{
		woodManager.add(new ItemStack(Blocks.log, 1, 0), new ItemStack(Items.coal, 2, 1));
		woodManager.add(new ItemStack(Blocks.log, 1, 1), new ItemStack(Items.coal, 1, 1), new ItemStack(FItems.nature.item(), 1, 3));
		woodManager.add(new ItemStack(Blocks.log, 1, 2), new ItemStack(Items.coal, 2, 1));
		woodManager.add(new ItemStack(Blocks.log, 1, 3), new ItemStack(Items.coal, 2, 1));
		
		kilnResin.addRecipe(new ItemStack(Blocks.log, 4, 1), new ItemStack(Blocks.log, 4, 1), new ItemStack(FItems.nature.item(), 2, 3), new ItemStack(FItems.nature.item(), 2, 3));
		kilnResin.addRecipe(new ItemStack(FItems.nature.item(), 9, 3), new ItemStack(FItems.nature.item(), 9, 3), new ItemStack(FItems.nature.item(), 1, 2), new ItemStack(FItems.nature.item(), 1, 2));
	}
	
	public static void addWorkbenchRecipes()
	{
		workbench.addRecipe(new OreStack("plankWood"), new OreStack("tool_file"), new ItemStack(ObjectRegistry.gearWood, 1, 5), ForestdayConfig.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ObjectRegistry.gearWood, 1, 5), new OreStack("tool_file"), new ItemStack(ObjectRegistry.gearWood, 1, 4), ForestdayConfig.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ObjectRegistry.gearWood, 1, 4), new OreStack("tool_file"), new ItemStack(ObjectRegistry.gearWood, 1, 3), ForestdayConfig.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ObjectRegistry.gearWood, 1, 3), new OreStack("tool_file"), new ItemStack(ObjectRegistry.gearWood, 1, 2), ForestdayConfig.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ObjectRegistry.gearWood, 1, 2), new OreStack("tool_file"), new ItemStack(ObjectRegistry.gearWood, 1, 1), ForestdayConfig.worktableBurnTime);
	}
	
	public static void addMachineRecipes()
	{
		
		//Furenace
		planRecipe.add(new ItemStack(Blocks.furnace), new ItemStack(FBlocks.Machine_Furnace_Base.item(), 1, 0), 2, new ItemStack[]{ new ItemStack(Items.brick, 16), new ItemStack(FItems.nature.item(), 8, 8)}, new ItemStack[]{ new ItemStack(Items.brick, 16), new ItemStack(FItems.nature.item(), 8, 8), new ItemStack(Blocks.chest)});
		CraftingHelper.removeAnyRecipe(new ItemStack(Blocks.furnace));
		addShapedRecipe(new ItemStack(Blocks.furnace), "SSS", "BHB", "BBB", 'S', "stone", 'B', Blocks.stonebrick);
		
		//Generator
		planRecipe.add(new ItemStack(FBlocks.Machine_Generator_Heat.item()), 2, new ItemStack[]{ new ItemStack(Items.brick, 8), new ItemStack(FItems.nature.item(), 4, 8), new ItemStack(Blocks.furnace)}, new ItemStack[]{ new ItemStack(Items.brick, 16), new ItemStack(FItems.nature.item(), 4, 8), new ItemStack(Blocks.chest)});
		
		//Campfire
		addShapedRecipe(new ItemStack(FBlocks.Machine_Wood_Base.item(), 1, 0), "   ", " + ", "+++", '+', "logWood");
		
		addShapelessRecipe(new ItemStack(FItems.curb.item(), 1, 0), "cobblestone", "cobblestone", "cobblestone", "cobblestone", "cobblestone", "cobblestone", "cobblestone", "cobblestone");
		addShapelessRecipe(new ItemStack(FItems.curb.item(), 1, 1), "obsidian", "obsidian", "obsidian", "obsidian", "obsidian", "obsidian", "obsidian", "obsidian");
		
		addShapedRecipe(new ItemStack(FItems.pot.item(), 1, 0), "   ", "+ +", "+++", '+', new ItemStack(Blocks.stone));
		addShapedRecipe(new ItemStack(FItems.pot.item(), 1, 1), "   ", "+ +", "+++", '+', "ingotBronze");
		addShapedRecipe(new ItemStack(FItems.pot.item(), 1, 2), "   ", "+ +", "+++", '+', "ingotIron");
		addShapedRecipe(new ItemStack(FItems.pot.item(), 1, 3), "   ", "+ +", "+++", '+', "ingotSteel");
		
		addShapedRecipe(new ItemStack(FItems.pot_holder.item()), "+++", "+ +", "+ +", '+', "logWood");
		addShapedRecipe(new ItemStack(FItems.pot_holder.item(), 1, 1), "+++", "+ +", "+ +", '+', Blocks.stone);
		addShapedRecipe(new ItemStack(FItems.pot_holder.item(), 1, 2), "+++", "+ +", "+ +", '+', "ingotBronze");
		addShapedRecipe(new ItemStack(FItems.pot_holder.item(), 1, 3), "+++", "+ +", "+ +", '+', "ingotIron");
		
		addShapedRecipe(new ItemStack(FBlocks.Machine_Wood_Base.item(), 1, 1), "---", "+++", "W W", '-', Blocks.crafting_table, '+', "slabWood", 'W', "logWood");
		addShapedRecipe(new ItemStack(FBlocks.Machine_Wood_Base.item(), 1, 2), "---", "+++", "WCW", '-', Blocks.crafting_table, '+', "slabWood", 'W', "logWood", 'C', Blocks.chest);
		addShapedRecipe(new ItemStack(FBlocks.Machine_Wood_Base.item(), 1, 3), "ILI", "ICI", "ILI", 'I', Items.iron_ingot, 'C', Blocks.chest, 'L', FBlocks.Gravel.item());
		
	}
	
	public static void addFuelToGenerator()
	{
		burning.addRecipe(new ItemStack(Items.coal, 1, 1), new ItemStack(FItems.nature.item(), 1, 7), 250, BurningMode.Charcoal);
	}
	
	public static void addCampfireRecipes()
	{
		campfire.addRecipe(new ItemStack(Blocks.red_mushroom), new ItemStack(Items.bowl),  new ItemStack(Items.mushroom_stew, 1),  0, 25);
		campfire.addRecipe(new ItemStack(Blocks.brown_mushroom), new ItemStack(Items.bowl),  new ItemStack(Items.mushroom_stew, 1),  0, 25);
		campfire.addRecipe(new ItemStack(Items.beef), new ItemStack(Items.cooked_beef), 0, 25);
		campfire.addRecipe(new ItemStack(Items.chicken), new ItemStack(Items.cooked_chicken), 0, 25);
		campfire.addRecipe(new ItemStack(Items.fish), new ItemStack(Items.cooked_fished), 0, 25);
		campfire.addRecipe(new ItemStack(Items.porkchop), new ItemStack(Items.cooked_porkchop), 0, 25);
		
		campfire.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone), 0, 10);
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
