package nedelosk.forestday.common.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nedelosk.forestday.api.crafting.BurningMode;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.IAirHeaterRecipe;
import nedelosk.forestday.api.crafting.IAlloySmelterRecipe;
import nedelosk.forestday.api.crafting.IBlastFurnaceRecipe;
import nedelosk.forestday.api.crafting.IBurnRecipe;
import nedelosk.forestday.api.crafting.ICokeFurnaceRecipe;
import nedelosk.forestday.api.crafting.IFluidHeaterRecipe;
import nedelosk.forestday.api.crafting.IKilnRecipe;
import nedelosk.forestday.api.crafting.IMaceratorRecipe;
import nedelosk.forestday.api.crafting.ISawRecipe;
import nedelosk.forestday.api.crafting.IWorkbenchRecipe;
import nedelosk.forestday.api.structure.crafting.BrokenItemStack;
import nedelosk.forestday.api.structure.crafting.BrokenRecipes;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.machines.brick.furnace.coke.CokeFurnaceRecipeManager;
import nedelosk.forestday.common.machines.brick.furnace.fluidheater.FluidHeaterRecipeManager;
import nedelosk.forestday.common.machines.brick.generator.heat.HeatGeneratorRecipeManager;
import nedelosk.forestday.common.machines.brick.kiln.KilnRecipeManager;
import nedelosk.forestday.common.machines.iron.saw.SawRecipeManager;
import nedelosk.forestday.common.machines.wood.workbench.WorkbenchRecipeManager;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import nedelosk.forestday.structure.airheater.crafting.AirHeaterRecipeManager;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipeManager;
import nedelosk.forestday.structure.base.blocks.BlockCoilGrinding;
import nedelosk.forestday.structure.blastfurnace.crafting.BlastFurnaceRecipeManager;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipeManager;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import nedelosk.nedeloskcore.utils.CraftingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingManager {
	
	public static ItemStack[] ingots = new ItemStack[] { new ItemStack(ForestdayItemRegistry.ingots, 1, 0), new ItemStack(ForestdayItemRegistry.ingots, 1, 1), new ItemStack(ForestdayItemRegistry.ingots, 1, 2), new ItemStack(ForestdayItemRegistry.ingots, 1, 3), new ItemStack(ForestdayItemRegistry.ingots, 1, 4), new ItemStack(ForestdayItemRegistry.ingots, 1, 5), new ItemStack(ForestdayItemRegistry.ingots, 1, 6), new ItemStack(ForestdayItemRegistry.ingots, 1, 7), new ItemStack(ForestdayItemRegistry.ingots, 1, 8), new ItemStack(ForestdayItemRegistry.ingots, 1, 9) };
	
	public static void removeRecipes()
	{
		CraftingHelper.removeAnyRecipe(new ItemStack(Items.bowl, 4));
	}
	
	public static void registerRecipes()
	{
		ForestdayCrafting.autoFileRecipe = new SawRecipeManager();
		ForestdayCrafting.kilnResinRecipes = new KilnRecipeManager();
		ForestdayCrafting.alloySmelterRecipes = new AlloySmelterRecipeManager();
		ForestdayCrafting.maceratorRecipes = new MaceratorRecipeManager();
		ForestdayCrafting.airHeaterRecipes = new AirHeaterRecipeManager();
		ForestdayCrafting.burningRecipe = new HeatGeneratorRecipeManager();
		ForestdayCrafting.blastFurnace = new BlastFurnaceRecipeManager();
		ForestdayCrafting.furnaceCokeRecipe = new CokeFurnaceRecipeManager();
		ForestdayCrafting.fluidHeaterRecipe = new FluidHeaterRecipeManager();
		ForestdayCrafting.workbenchRecipe = new WorkbenchRecipeManager();
		
		ISawRecipe autoFileRecipe = ForestdayCrafting.autoFileRecipe;
		IAlloySmelterRecipe alloySmelter = ForestdayCrafting.alloySmelterRecipes;
		IKilnRecipe kilnResin = ForestdayCrafting.kilnResinRecipes;
		IMaceratorRecipe macerator = ForestdayCrafting.maceratorRecipes;
		IBurnRecipe burning = ForestdayCrafting.burningRecipe;
		IAirHeaterRecipe airHeater = ForestdayCrafting.airHeaterRecipes;
		IBlastFurnaceRecipe blastFurnace = ForestdayCrafting.blastFurnace;
		ICokeFurnaceRecipe furnaceCoke = ForestdayCrafting.furnaceCokeRecipe;
		IFluidHeaterRecipe fluidHeater = ForestdayCrafting.fluidHeaterRecipe;
		IWorkbenchRecipe workbench = ForestdayCrafting.workbenchRecipe;
		
		//Block Crafting
		loadMachineRecipes();
		
		//Item Crafting
		loadCraftingRecipes();
		loadIngotRecipes();
		
		loadSmeltingRecipes();
		
		loadToolRecipes();
		
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.machineItems, 12, 0), new ItemStack(Items.dye, 1, 11), new ItemStack(Items.dye, 1, 11), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.gunpowder), new ItemStack(Items.gunpowder));
		
		//Forestday Crafting
		
		loadWorkbenchRecipe(workbench);
		
		//Blast Furnace
		loadBlastFurnaceRecipes(blastFurnace);
		
		//Air Heater
		loadAirHeaterRecipes(airHeater);
		
		//Alloy Smelter
		loadAlloySmelterRecipes(alloySmelter);
		
		//Fluid Heater
		loadFurnaceFluidHeater(fluidHeater);
		
		//Saw
		loadSawRecipes(autoFileRecipe);
		
		//Kiln
		loadKilnRecipe( kilnResin);
		
		//Macerator
		loadMaceratorRecipes(macerator);
		
		//Burning
		registerFuel(burning);
		
		//Coke Furnace
		loadFurnaceCokeRecipes(furnaceCoke);
		
		//Structures Crafting Recipes
		loadStructureRecipes();
		
		//Ore Recipes
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.nuggets, 9, 0), "ingotCopper"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.nuggets, 9, 1), "ingotTin"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.nuggets, 9, 2), "ingotBronze"));
		
		loadCoils();
		
	}
	
	public static void loadWorkbenchRecipe(IWorkbenchRecipe recipe)
	{
		recipe.addRecipe(new OreStack("plankWood"), new OreStack("toolFile"), new ItemStack(ForestdayItemRegistry.gearWood, 1, 5), 20);
		recipe.addRecipe(new ItemStack(ForestdayItemRegistry.gearWood, 1, 5), new OreStack("toolFile"), new ItemStack(ForestdayItemRegistry.gearWood, 1, 4), 17);
		recipe.addRecipe(new ItemStack(ForestdayItemRegistry.gearWood, 1, 4), new OreStack("toolFile"), new ItemStack(ForestdayItemRegistry.gearWood, 1, 3), 15);
		recipe.addRecipe(new ItemStack(ForestdayItemRegistry.gearWood, 1, 3), new OreStack("toolFile"), new ItemStack(ForestdayItemRegistry.gearWood, 1, 2), 13);
		recipe.addRecipe(new ItemStack(ForestdayItemRegistry.gearWood, 1, 2), new OreStack("toolFile"), new ItemStack(ForestdayItemRegistry.gearWood, 1, 1), 60);
		recipe.addRecipe(new ItemStack(Blocks.planks, 4), new OreStack("toolKnife"), new ItemStack(ForestdayItemRegistry.woodBucket), 270);
		recipe.addRecipe(new OreStack("logWood"), new OreStack("toolAdze"), new ItemStack(Items.bowl, 2), 160);
	}

	public static void loadCraftingRecipes()
	{
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayItemRegistry.buildBrickItems, 8, 0), "+++", "+-+", "+++", '+', new ItemStack(ForestdayBlockRegistry.loam), '-', new ItemStack(Items.water_bucket));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayItemRegistry.nature, 1, 4), "+++", "D-D", "+++", '+', new ItemStack(ForestdayBlockRegistry.loam), '-', new ItemStack(Items.water_bucket), 'D', Blocks.dirt);
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.loam, 4),  "CCC",
                "CGE",
                "EEE", 'E', new ItemStack(Blocks.dirt), 'G', new ItemStack(Blocks.gravel), 'C', new ItemStack(Items.clay_ball));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.flintAxe, 1), Items.stick, Items.flint, Items.flint, Items.flint);
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayItemRegistry.flintAxe, 1),  "HFF",
                "HWF",
                "HWH", 'W', new ItemStack(Items.stick), 'F', new ItemStack(Items.flint));
		GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.workbench, 1),  
				"TTT",
                "SSS",
                "WHW", 'W', "logWood", 'S', "slabWood", 'T', Blocks.crafting_table));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayBlockRegistry.workbench, 1, 1), new ItemStack(ForestdayBlockRegistry.workbench, 1), new ItemStack(Blocks.chest));
	
		GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.workbench, 1, 1),  
				"TTT",
                "SSS",
                "WCW", 'W', "logWood", 'S', "slabWood", 'C', new ItemStack(Blocks.chest), 'T', Blocks.crafting_table));
                }

	
	
	public static void loadMachineRecipes()
	{
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.machineKiln), "WLW",
                "WLW",
                "WLW", 'W', ForestdayBlockRegistry.trunkMedium, 'L', ForestdayBlockRegistry.loam);

		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.machineKiln, 1, 1), 
				"ILI",
				"ILI",
				"ILI", 'I', Blocks.iron_block, 'L', ForestdayBlockRegistry.loam);

		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.machineFile, 1, 0), 
				"BLL",
				"III",
				"IRI", 'I', Blocks.iron_block, 'R', Blocks.redstone_block, 'B', Items.iron_ingot, 'L', ForestdayItemRegistry.machineItems);
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.machineFurnace, 1, 0), "+-+", "+F+", "+++", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), 'F', Blocks.furnace, '-', new ItemStack(Blocks.trapdoor));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.machineFurnace, 1, 1), "+-+", "IFI", "+++", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), 'F', new ItemStack(ForestdayBlockRegistry.machineFurnace, 1, 0), '-', new ItemStack(Blocks.trapdoor), 'I', new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 3));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.machineGenerator, 1, 0), "+-+", "FFF", "+P+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), 'F', Blocks.furnace, '-', new ItemStack(Blocks.trapdoor), 'P', Blocks.piston);
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.machineFan, 1, 0), "+-+", "-R-", "+-+", '+', new ItemStack(Blocks.stonebrick, 1, 0), 'R', Blocks.redstone_block, '-', new ItemStack(ForestdayItemRegistry.rods, 1, 1));
		
	}
	
	public static void loadIngotRecipes()
	{
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(ingots[0], "+++","+++","+++", '+',"nuggetCopper"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(ingots[1], "+++","+++","+++", '+',"nuggetTin"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(ingots[2], "+++","+++","+++", '+',"nuggetBronze"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 0), "+++","+++","+++", '+',"ingotCopper"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 1), "+++","+++","+++", '+',"ingotTin"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 2), "+++","+++","+++", '+',"ingotBronze"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 3), "+++","+++","+++", '+',"ingotAlloyRed"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 4), "+++","+++","+++", '+',"ingotAlloyBlue"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 5), "+++","+++","+++", '+',"ingotAlloyBlueDark"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 6), "+++","+++","+++", '+',"ingotAlloyYellow"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 7), "+++","+++","+++", '+',"ingotAlloyBrown"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 8), "+++","+++","+++", '+',"ingotAlloyGreen"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 9), "+++","+++","+++", '+',"ingotSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 10), "+++","+++","+++", '+',"ingotLightSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 11), "+++","+++","+++", '+',"ingotDarkSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 12), "+++","+++","+++", '+',"ingotObsidian"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 13), "+++","+++","+++", '+',"ingotEnderium"));
		
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metal, 1, 0), "+++","+O+","+++", '+',"plateSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metal, 1, 1), "+++","+O+","+++", '+',"plateLightSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metal, 1, 2), "+++","+O+","+++", '+',"plateDarkSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metal, 1, 3), "+++","+O+","+++", '+',"plateObsidian"));
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack(ForestdayBlockRegistry.metal, 1, 4), "+++","+O+","+++", '+',"plateEnderium"));
		
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 0), "blockCopper"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 1), "blockTin"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 2), "blockBronze"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 3), "blockAlloyRed"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 4), "blockAlloyBlue"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 5), "blockAlloyBlueDark"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 6), "blockAlloyYellow"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 7), "blockAlloyBrown"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 8), "blockAlloyGreen"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 9), "blockSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 10), "blockLightSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 11), "blockDarkSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 12), "blockObsidian"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.ingots, 9, 13), "blockEnderium"));
		
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.buildPlates, 2, 0), "ingotSteel", "ingotSteel", "ingotSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.buildPlates, 2, 1), "ingotLightSteel", "ingotLightSteel", "ingotLightSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.buildPlates, 2, 2), "ingotDarkSteel", "ingotDarkSteel", "ingotDarkSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.buildPlates, 2, 3), "ingotObsidian", "ingotObsidian", "ingotObsidian"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.buildPlates, 2, 4), "ingotEnderium", "ingotEnderium", "ingotEnderium"));
		
	}
	
	public static void loadSmeltingRecipes()
	{
		GameRegistry.addSmelting(new ItemStack(ForestdayBlockRegistry.oreBlock, 1, 0), new ItemStack(ForestdayItemRegistry.ingots, 1, 0), 0);
		GameRegistry.addSmelting(new ItemStack(ForestdayBlockRegistry.oreBlock, 1, 1), new ItemStack(ForestdayItemRegistry.ingots, 1, 1), 0);
		GameRegistry.addSmelting(new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 0), new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 1), 0);
		
	}
	
	public static void loadToolRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 0), Items.leather, Items.stick, Items.stick);
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 1), Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone);
		GameRegistry.addRecipe( new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 2), Items.iron_ingot, "toolFile", Items.iron_ingot));
		GameRegistry.addRecipe( new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 3), Items.diamond, "toolFile", Items.diamond));
		GameRegistry.addRecipe( new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 4), Items.stick, Items.leather, Items.stick, "toolFile"));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 5), Items.flint, Items.flint);
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 6), Items.flint, Items.flint, Items.iron_ingot, Items.iron_ingot);
		GameRegistry.addRecipe( new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 7), Items.stick, "toolFile", Items.stick));
		GameRegistry.addRecipe( new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 8), Blocks.stone, Blocks.stone, Blocks.stone, "toolFile"));
		GameRegistry.addRecipe( new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 9), Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, "toolFile"));
		GameRegistry.addRecipe( new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 10), Items.stick, Items.stick, Items.string, Items.string, "toolFile"));
		GameRegistry.addRecipe( new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.toolparts, 1, 11), Items.stick, Items.stick, Items.stick, "logWood", Items.stick, Items.string, Items.string, "toolFile"));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.file), new ItemStack(ForestdayItemRegistry.toolparts, 1, 0), new ItemStack(ForestdayItemRegistry.toolparts, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.fileIron), new ItemStack(ForestdayItemRegistry.toolparts, 1, 0), new ItemStack(ForestdayItemRegistry.toolparts, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.fileDiamond), new ItemStack(ForestdayItemRegistry.toolparts, 1, 0), new ItemStack(ForestdayItemRegistry.toolparts, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.cutter), new ItemStack(ForestdayItemRegistry.toolparts, 1, 6), new ItemStack(ForestdayItemRegistry.toolparts, 1, 7));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.toolKnife), new ItemStack(ForestdayItemRegistry.toolparts, 1, 4), new ItemStack(ForestdayItemRegistry.toolparts, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.adze), new ItemStack(ForestdayItemRegistry.toolparts, 1, 10), new ItemStack(ForestdayItemRegistry.toolparts, 1, 8));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.adzeLong), new ItemStack(ForestdayItemRegistry.toolparts, 1, 11), new ItemStack(ForestdayItemRegistry.toolparts, 1, 9));
	}
	
	public static void loadStructureRecipes()
	{
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.rods, 1, 0), "ingotBronze", new ItemStack(ForestdayItemRegistry.fileIron, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.rods, 1, 0), "ingotBronze", new ItemStack(ForestdayItemRegistry.fileDiamond, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.rods, 1, 1), new ItemStack(Items.iron_ingot), new ItemStack(ForestdayItemRegistry.fileIron, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.rods, 1, 1), new ItemStack(Items.iron_ingot), new ItemStack(ForestdayItemRegistry.fileDiamond, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.rods, 1, 2), "ingotSteel", new ItemStack(ForestdayItemRegistry.fileIron, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.rods, 1, 2), "ingotSteel", new ItemStack(ForestdayItemRegistry.fileDiamond, 1, OreDictionary.WILDCARD_VALUE)));
		
		//Bus
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrick, 1, 0), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrickFluid, 1, 0), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));	
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrick, 1, 1), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 1), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrickFluid, 1, 1), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 1), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrick, 1, 2), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 2), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrickFluid, 1, 2), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 2), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrick, 1, 3), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 3), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrickFluid, 1, 3), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 3), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrick, 1, 4), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 4), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrickFluid, 1, 4), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 4), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrick, 1, 5), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 5), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busBrickFluid, 1, 5), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 5), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetal, 1, 0), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 0), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetalFluid, 1, 0), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 0), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetal, 1, 1), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 1), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetalFluid, 1, 1), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 1), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetal, 1, 2), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 2), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetalFluid, 1, 2), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 2), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetal, 1, 3), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 3), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetalFluid, 1, 3), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 3), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetal, 1, 4), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 4), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.chest));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.busMetalFluid, 1, 4), "+-+", "-C-", "+-+", '+', new ItemStack(ForestdayBlockRegistry.metal, 1, 4), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 3), 'C', new ItemStack(Blocks.glass));
		
		addShapedRecipe(new ItemStack(ForestdayBlockRegistry.alloySmelter, 1, 0), "+++", "+R+", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 2), 'R', new ItemStack(Blocks.redstone_block));
		addShapedRecipe(new ItemStack(ForestdayBlockRegistry.alloySmelter, 1, 1), "+C+", "+R+", "+C+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), 'R', new ItemStack(Blocks.redstone_block), 'C', new ItemStack(ForestdayItemRegistry.ingots, 1, 0));
		addShapedRecipe(new ItemStack(ForestdayBlockRegistry.macerator, 1, 0), "+++", "+R+", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 2), 'R', new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 5));
		addShapedRecipe(new ItemStack(ForestdayBlockRegistry.macerator, 1, 1), "+C+", "+R+", "+C+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), 'R', new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 5), 'C', new ItemStack(ForestdayItemRegistry.ingots, 1, 4));
		addShapedRecipe(new ItemStack(ForestdayBlockRegistry.airHeater, 1, 0), "+++", "+R+", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 1), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 2), 'R', new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 3));
		addShapedRecipe(new ItemStack(ForestdayBlockRegistry.airHeater, 1, 1), "+C+", "+R+", "+C+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 1), 'R', new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 3), 'C', new ItemStack(ForestdayItemRegistry.ingots, 1, 3));
		addShapedRecipe(new ItemStack(ForestdayBlockRegistry.blastFurnace, 1, 0), "+++", "+R+", "+-+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 2), '-', new ItemStack(ForestdayItemRegistry.hatch, 1, 2), 'R', new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 7));
		addShapedRecipe(new ItemStack(ForestdayBlockRegistry.blastFurnace, 1, 1), "+C+", "+R+", "+C+", '+', new ItemStack(ForestdayBlockRegistry.bricks, 1, 2), 'R', new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 7), 'C', new ItemStack(ForestdayItemRegistry.ingots, 1, 7));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayItemRegistry.hatch, 1, 0), "+-+", "+R+", "+++", '+', new ItemStack(Items.iron_ingot, 1, 0), '-', new ItemStack(Blocks.trapdoor, 1, 0), 'R', new ItemStack(ForestdayItemRegistry.hatch, 1, 3));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayItemRegistry.hatch, 1, 1), "+++", "+R+", "+-+", '+', new ItemStack(Items.iron_ingot, 1, 0), '-', new ItemStack(Blocks.trapdoor, 1, 0), 'R', new ItemStack(ForestdayItemRegistry.hatch, 1, 3));
		addShapedRecipe(new ItemStack(ForestdayItemRegistry.hatch, 1, 2), "+C+", "HRH", "+-+", '+', new ItemStack(Items.iron_ingot, 1, 0), '-', new ItemStack(Blocks.glass_pane, 1, 0), 'R', Items.redstone, 'C', "ingotCopper", 'H', new ItemStack(ForestdayItemRegistry.nature, 1, 2));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayItemRegistry.hatch, 1, 3), "+-+", "-+-", "+-+", '+', new ItemStack(Blocks.planks), '-', new ItemStack(Items.stick));
		
		//Bricks
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.bricks, 1, 0), "+++", "---", "+++", '+', new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 1), '-', new ItemStack(ForestdayItemRegistry.buildMortalItems, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.bricks, 1, 1), "+++", "---", "+++", '+', new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 3), '-', new ItemStack(ForestdayItemRegistry.buildMortalItems, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.bricks, 1, 2), "+++", "---", "+++", '+', new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 5), '-', new ItemStack(ForestdayItemRegistry.buildMortalItems, 1, 2));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 4), new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 0), new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 0), new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 2), new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 2));
		
		addShapelessRecipe(new ItemStack(ForestdayItemRegistry.buildMortalItems, 8, 0), new ItemStack(Blocks.gravel), "limestone", new ItemStack(Blocks.sand), new ItemStack(ForestdayBlockRegistry.loam), Items.water_bucket);
		addShapelessRecipe(new ItemStack(ForestdayItemRegistry.buildMortalItems, 8, 1), new ItemStack(Blocks.gravel), "dustLime", new ItemStack(Blocks.sand), new ItemStack(ForestdayBlockRegistry.loam), "dustStone", Items.water_bucket, new ItemStack(ForestdayItemRegistry.metallurgy, 1, 1));
		addShapelessRecipe(new ItemStack(ForestdayItemRegistry.buildMortalItems, 4, 2), new ItemStack(Blocks.gravel), "dustLime", new ItemStack(Blocks.sand), new ItemStack(ForestdayBlockRegistry.loam), new ItemStack(ForestdayItemRegistry.nature, 1, 7), new ItemStack(ForestdayItemRegistry.nature, 1, 7), new ItemStack(ForestdayItemRegistry.nature, 1, 1), Items.water_bucket);
		
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayBlockRegistry.grindingCoil, 1, 0), "logWood", "logWood", "logWood"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayBlockRegistry.grindingCoil, 1, 1), "stone", "stone", "stone", "stone", "stone", "stone"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayBlockRegistry.grindingCoil, 1, 2), "blockIron", "blockIron", "blockIron"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayBlockRegistry.grindingCoil, 1, 3), "blockSteel", "blockSteel", "blockSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayBlockRegistry.grindingCoil, 1, 4), "blockDarkSteel", "blockDarkSteel", "blockDarkSteel"));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayBlockRegistry.grindingCoil, 1, 5), "blockEnderium", "blockEnderium", "blockEnderium"));
		
		ItemStack coilGrinding0 = new ItemStack(ForestdayItemRegistry.coilGrinding,  1, 0);
		coilGrinding0.setTagCompound(new NBTTagCompound());
		coilGrinding0.getTagCompound().setInteger("Damage", BlockCoilGrinding.coilMaxSharpness[coilGrinding0.getItemDamage()]);
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(coilGrinding0, "+-+", "-C-", "+-+", '+', Items.flint, '-', new ItemStack(Blocks.stone), 'C', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 9)));
		
		ItemStack coilGrinding1 = new ItemStack(ForestdayItemRegistry.coilGrinding,  1, 1);
		coilGrinding1.setTagCompound(new NBTTagCompound());
		coilGrinding1.getTagCompound().setInteger("Damage", BlockCoilGrinding.coilMaxSharpness[coilGrinding1.getItemDamage()]);
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(coilGrinding1, "+-+", "-C-", "+-+", '+', Items.flint, '-', new ItemStack(Items.iron_ingot), 'C', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 9)));
		
		ItemStack coilGrinding2 = new ItemStack(ForestdayItemRegistry.coilGrinding,  1, 2);
		coilGrinding2.setTagCompound(new NBTTagCompound());
		coilGrinding2.getTagCompound().setInteger("Damage", BlockCoilGrinding.coilMaxSharpness[coilGrinding2.getItemDamage()]);
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(coilGrinding2, "+-+", "-C-", "+-+", '+', Items.flint, '-', new ItemStack(ForestdayItemRegistry.ingots, 1, 7), 'C', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 9)));
		
		ItemStack coilGrinding3 = new ItemStack(ForestdayItemRegistry.coilGrinding,  1, 3);
		coilGrinding3.setTagCompound(new NBTTagCompound());
		coilGrinding3.getTagCompound().setInteger("Damage", BlockCoilGrinding.coilMaxSharpness[coilGrinding3.getItemDamage()]);
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(coilGrinding3, "+-+", "-C-", "+-+", '+', Items.flint, '-', new ItemStack(ForestdayItemRegistry.ingots, 1, 9), 'C', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 9)));
		
		ItemStack coilGrinding4 = new ItemStack(ForestdayItemRegistry.coilGrinding,  1, 4);
		coilGrinding4.setTagCompound(new NBTTagCompound());
		coilGrinding4.getTagCompound().setInteger("Damage", BlockCoilGrinding.coilMaxSharpness[coilGrinding4.getItemDamage()]);
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(coilGrinding4, "+-+", "-C-", "+-+", '+', Items.flint, '-', new ItemStack(ForestdayItemRegistry.ingots, 1, 11), 'C', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 9)));
		
		ItemStack coilGrinding5 = new ItemStack(ForestdayItemRegistry.coilGrinding,  1, 5);
		coilGrinding5.setTagCompound(new NBTTagCompound());
		coilGrinding5.getTagCompound().setInteger("Damage", BlockCoilGrinding.coilMaxSharpness[coilGrinding5.getItemDamage()]);
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(coilGrinding5, "+-+", "-C-", "+-+", '+', Items.flint, '-', new ItemStack(ForestdayItemRegistry.dust, 1, 6), 'C', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 9)));
	}
	
	public static void loadCoils()
	{
		
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 0), new ItemStack(ForestdayItemRegistry.ingots, 1, 3), new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 1), new ItemStack(ForestdayItemRegistry.ingots, 1, 4), new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 2), new ItemStack(ForestdayItemRegistry.ingots, 1, 5), new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 3), new ItemStack(ForestdayItemRegistry.ingots, 1, 6), new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 4), new ItemStack(ForestdayItemRegistry.ingots, 1, 7), new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addShapelessRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 5), new ItemStack(ForestdayItemRegistry.ingots, 1, 8), new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 6), "ingotCopper", new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 7), "ingotTin", new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 8), "ingotBronze", new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 9), "ingotIron", new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack(ForestdayItemRegistry.coilHeat, 1, 10), "ingotSteel", new ItemStack(ForestdayItemRegistry.cutter, 1, OreDictionary.WILDCARD_VALUE)));
		
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.heatCoil, 1, 0), "+++", "+-+", "+++", '+', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 0), '-', new ItemStack(ForestdayItemRegistry.rods, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.heatCoil, 1, 1), "+++", "+-+", "+++", '+', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 1), '-', new ItemStack(ForestdayItemRegistry.rods, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.heatCoil, 1, 2), "+++", "+-+", "+++", '+', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 2), '-', new ItemStack(ForestdayItemRegistry.rods, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.heatCoil, 1, 3), "+++", "+-+", "+++", '+', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 3), '-', new ItemStack(ForestdayItemRegistry.rods, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.heatCoil, 1, 4), "+++", "+-+", "+++", '+', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 4), '-', new ItemStack(ForestdayItemRegistry.rods, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.heatCoil, 1, 5), "+++", "+-+", "+++", '+', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 5), '-', new ItemStack(ForestdayItemRegistry.rods, 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ForestdayBlockRegistry.heatCoil, 1, 6), "+++", "+-+", "+++", '+', new ItemStack(ForestdayItemRegistry.coilHeat, 1, 6), '-', new ItemStack(ForestdayItemRegistry.rods, 1, 0));
	}
	
	public static void loadAirHeaterRecipes(IAirHeaterRecipe recipes)
	{
		recipes.addRecipe(new FluidStack(FluidRegistry.getFluid("gas_blast"), ForestdayConfig.airHeaterGasInput), new FluidStack(FluidRegistry.getFluid("air_hot"), ForestdayConfig.airHeaterGasOutput), 40, 300);
	}
	
	public static void loadBlastFurnaceRecipes(IBlastFurnaceRecipe recipes)
	{
		recipes.addRecipe(new ItemStack[]{ new ItemStack(ForestdayItemRegistry.metallurgy, 4, 0), new ItemStack(ForestdayItemRegistry.metallurgy, 6, 1), new ItemStack(Blocks.iron_ore, 2), new ItemStack(Blocks.sand, 4)}, new FluidStack[] { new FluidStack(FluidRegistry.getFluid("slag"), 1750), new FluidStack(FluidRegistry.getFluid("pig_iron"), 250)}, 1900, 6500);
	}
	
	public static void loadAlloySmelterRecipes(IAlloySmelterRecipe recipes)
	{
		recipes.addRecipe(new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.redstone, 8), new ItemStack(ForestdayItemRegistry.ingots, 3, 3), 50, 2000);
		recipes.addRecipe(new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.dye, 16, 12), new ItemStack(ForestdayItemRegistry.ingots, 3, 4), 200, 1500);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.ingots, 3, 4), new ItemStack(Items.dye, 6, 0), new ItemStack(ForestdayItemRegistry.ingots, 1, 5), 700, 1450);
		recipes.addRecipe(new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.dye, 24, 4), new ItemStack(ForestdayItemRegistry.ingots, 3, 5), 300, 1450);
		recipes.addRecipe(new ItemStack(Items.iron_ingot, 2), new ItemStack(Items.coal, 4, 0), new ItemStack(ForestdayItemRegistry.ingots, 3, 10), 800, 1500);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.ingots, 3, 0), "ingotTin", new ItemStack(ForestdayItemRegistry.ingots, 3, 2), 300, 700);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.nature, 8, 7), new ItemStack(ForestdayItemRegistry.nature, 8, 7), new ItemStack(ForestdayItemRegistry.buildBrickItems, 2, 2), 80, 180);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 2), new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 2), new ItemStack(ForestdayItemRegistry.buildBrickItems, 1, 3), 180, 360);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.ingots, 3, 6), new ItemStack(ForestdayItemRegistry.ingots, 6, 3), new ItemStack(ForestdayItemRegistry.ingots, 1, 7), 600, 625);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.ingots, 3, 4), new ItemStack(Items.gold_ingot, 16), new ItemStack(ForestdayItemRegistry.ingots, 2, 6), 350, 500);
		recipes.addRecipe(new ItemStack(Items.brick, 2), new ItemStack(ForestdayItemRegistry.buildBrickItems, 2, 4), new ItemStack(ForestdayItemRegistry.buildBrickItems, 2, 5), 300, 600);
		
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.powder, 2, 0), new ItemStack(ForestdayItemRegistry.powder, 2, 0), new ItemStack(ForestdayItemRegistry.ingots, 4, 0), 600, 800);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.powder, 2, 1), new ItemStack(ForestdayItemRegistry.powder, 2, 1), new ItemStack(ForestdayItemRegistry.ingots, 4, 1), 600, 800);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.powder, 2, 2), new ItemStack(ForestdayItemRegistry.powder, 2, 2), new ItemStack(Items.iron_ingot, 4), 600, 800);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.powder, 2, 3), new ItemStack(ForestdayItemRegistry.powder, 2, 3), new ItemStack(Items.gold_ingot, 4), 600, 800);
	}
	
	public static void loadMaceratorRecipes(IMaceratorRecipe recipes)
	{
		recipes.addRecipe(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stonebrick, 1, 2), 1, 50, 100, true);
		recipes.addRecipe(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.cobblestone), 50, 200, 100, true);
		recipes.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.gravel), 1, 99, 100, true);
		recipes.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.sand), 100, 300, 100, true);
		recipes.addRecipe(new ItemStack(Blocks.gravel), new ItemStack(Blocks.sand), 100, 300, 100, true);
		recipes.addRecipe(new ItemStack(Blocks.gravel, 2), new ItemStack(Items.flint), 1, 99, 100, true);
		
		BrokenRecipes.addMaceratorOreRecipe("oreCopper", new ItemStack(ForestdayItemRegistry.powder, 2, 0), new ItemStack(ForestdayItemRegistry.dust, 4, 0), recipes);
		BrokenRecipes.addMaceratorOreRecipe("oreTin", new ItemStack(ForestdayItemRegistry.powder, 2, 1), new ItemStack(ForestdayItemRegistry.dust, 4, 1), recipes);
		BrokenRecipes.addMaceratorOreRecipe("oreIron", new ItemStack(ForestdayItemRegistry.powder, 2, 2), new ItemStack(ForestdayItemRegistry.dust, 4, 2), recipes);
		BrokenRecipes.addMaceratorOreRecipe("oreGold", new ItemStack(ForestdayItemRegistry.powder, 2, 3), new ItemStack(ForestdayItemRegistry.dust, 4, 3), recipes);
		BrokenRecipes.addMaceratorOreRecipe("oreLapis", new ItemStack(ForestdayItemRegistry.powder, 2, 5), new ItemStack(ForestdayItemRegistry.dust, 4, 5), recipes);
		BrokenRecipes.addMaceratorOreRecipe("oreEmerald", new ItemStack(ForestdayItemRegistry.powder, 2, 7), new ItemStack(ForestdayItemRegistry.dust, 4, 7), recipes);
		BrokenRecipes.addMaceratorOreRecipe("oreDiamond", new ItemStack(ForestdayItemRegistry.powder, 2, 6), new ItemStack(ForestdayItemRegistry.dust, 4, 6), recipes);
		BrokenRecipes.addMaceratorOreRecipe("oreCoal", new ItemStack(ForestdayItemRegistry.powder, 2, 8), new ItemStack(ForestdayItemRegistry.dust, 4, 8), recipes);
		BrokenRecipes.addMaceratorOreRecipe("stoneLime", new ItemStack(ForestdayItemRegistry.metallurgy, 4, 1), new ItemStack(ForestdayItemRegistry.metallurgy, 4, 1), recipes);
		
		for(BrokenItemStack recipe : BrokenRecipes.maceratorRecipes)
		{
			if(recipe.getInput() != null)
			{
			recipes.addRecipe(recipe.getInput(), recipe.getOutput(), recipe.getMinRoughness(), 100, recipe.getMaxRoughness(), false);
			}
			if(recipe.getOreDict() != null)
			{
			recipes.addRecipe(recipe.getOreDict(), recipe.getOutput(), recipe.getMinRoughness(), 100, recipe.getMaxRoughness(), false);
			}
		}
	}
	
	public static void loadKilnRecipe(IKilnRecipe resinRecipes)
	{
		//Kiln Resin
		resinRecipes.addRecipe(new ItemStack(Blocks.log, 8, 1), new ItemStack(Blocks.log, 8, 1), new ItemStack(ForestdayItemRegistry.nature, 4, 3), new ItemStack(ForestdayItemRegistry.nature, 2, 2));
		resinRecipes.addRecipe(new ItemStack(ForestdayBlockRegistry.logNature, 8, 0), new ItemStack(ForestdayBlockRegistry.logNature, 8, 0), new ItemStack(ForestdayItemRegistry.nature, 8, 3), new ItemStack(ForestdayItemRegistry.nature, 4, 2));
		resinRecipes.addRecipe(new ItemStack(ForestdayItemRegistry.nature, 2, 3), new ItemStack(ForestdayItemRegistry.nature, 2, 3), new ItemStack(ForestdayItemRegistry.nature, 2, 2), new ItemStack(ForestdayItemRegistry.nature, 2, 2));
		
	}
	
	public static void loadSawRecipes(ISawRecipe recipes)
	{
		recipes.addRecipe(new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.planks, 4, 0));
		recipes.addRecipe(new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.planks, 4, 1));
		recipes.addRecipe(new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.planks, 4, 2));
		recipes.addRecipe(new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.planks, 4, 3));
		recipes.addRecipe(new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.planks, 4, 4));
		recipes.addRecipe(new ItemStack(Blocks.log2, 1, 1), new ItemStack(Blocks.planks, 4, 5));
		recipes.addRecipe(new ItemStack(Blocks.planks), new ItemStack(Items.stick, 1));
	}
	
	public static void loadFurnaceCokeRecipes(ICokeFurnaceRecipe recipes)
	{
		recipes.addRecipe(new ItemStack(Items.coal, 1, 0), new ItemStack(ForestdayItemRegistry.metallurgy, 1, 0), /*new ItemStack(ForestdayItemRegistry.metallurgy, 4, 2)*/null, null, new FluidStack(FluidRegistry.getFluid("tar"), 20));
	}
	
	public static void registerFuel(IBurnRecipe recipes)
	{
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.nature, 1, 4), new ItemStack(ForestdayItemRegistry.nature, 2, 7), 1600, BurningMode.Peat);
		
		recipes.addRecipe("logWood", new ItemStack(ForestdayItemRegistry.nature, 2, 7), 300, BurningMode.Wood);
		
		recipes.addRecipe("plankWood", new ItemStack(ForestdayItemRegistry.nature, 2, 7), 300, BurningMode.Wood);
		recipes.addRecipe("fenceWood", new ItemStack(ForestdayItemRegistry.nature, 2, 7), 300, BurningMode.Wood);
		recipes.addRecipe("slabWood", new ItemStack(ForestdayItemRegistry.nature, 2, 7), 300, BurningMode.Wood);
		recipes.addRecipe(new ItemStack(Items.coal, 1, 0), new ItemStack(ForestdayItemRegistry.nature, 2, 7), 300, BurningMode.Coal);
		recipes.addRecipe(new ItemStack(Items.coal, 1, 1), new ItemStack(ForestdayItemRegistry.nature, 2, 7), 300, BurningMode.Coal);
	}
	
	public static void loadFurnaceFluidHeater(IFluidHeaterRecipe recipes)
	{
		recipes.addRecipe(new FluidStack(FluidRegistry.getFluid("pig_iron"), 100), new FluidStack(FluidRegistry.getFluid("air"), 12000), new FluidStack(FluidRegistry.getFluid("molten_steel"), 75), 500);
		recipes.addRecipe(new FluidStack(FluidRegistry.LAVA, 16000), new FluidStack(FluidRegistry.getFluid("air"), 100), new FluidStack(FluidRegistry.getFluid("air_hot"), 30), 40);
		recipes.addRecipe(new ItemStack(ForestdayItemRegistry.nature, 2, 3), new FluidStack(FluidRegistry.getFluid("resin"), 25), 75);
		recipes.addRecipe(new FluidStack(FluidRegistry.getFluid("resin"), 100), new FluidStack(FluidRegistry.getFluid("rubber"), 25), 75);
	}
	
	public static void addShapedRecipe(ItemStack stack, Object... obj)
	{
		GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(stack, obj));
	}
	
	public static void addShapelessRecipe(ItemStack stack, Object... obj)
	{
		GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(stack, obj));
	}
	
}
