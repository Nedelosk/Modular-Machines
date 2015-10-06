package nedelosk.modularmachines.common.core.manager;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.IWorkbenchRecipe;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.crafting.BlastFurnaceRecipeManager;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.alloysmelter.RecipeAlloySmelter;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.centrifuge.RecipeCentrifuge;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.pulverizer.RecipePulverizer;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.sawmill.RecipeSawMill;
import nedelosk.modularmachines.common.modular.utils.MaterialManager;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import nedelosk.nedeloskcore.common.core.registry.NCItemManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeManager {

	public static void init()
	{
		ModularMachinesApi.blastFurnace = new BlastFurnaceRecipeManager();
		
		registerSawMillRecipes();
		registerPulverizerRecipes();
		registerAlloySmelterRecipes();
		registerCentrifugeRecipes();
		registerMetalRecipes();
		registerModuleRecipes();
		registerModularComponentRecipes();
		ModularMachinesApi.blastFurnace.addRecipe(10, new FluidStack[]{ new FluidStack(FluidRegistry.getFluid("slag"), 1000), new FluidStack(FluidRegistry.getFluid("white_pig_iron"), 220)}, new Object[]{ new ItemStack(Blocks.iron_ore, 1)}, 1200);
	}
	
	public static void registerModuleRecipes()
	{
		addShapedRecipe(new ItemStack(MMBlockManager.Modular_Assembler.item()), "+++", "+W+", "+++", '+', "plateIron", 'W', Blocks.crafting_table);
	}
	
	public static void registerSawMillRecipes()
	{
		RecipeRegistry.registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log, 1, 0), new RecipeItem[]{new RecipeItem(new ItemStack(Blocks.planks, 6, 0))}, 10, 300));
		RecipeRegistry.registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log, 1, 1), new RecipeItem[]{new RecipeItem(new ItemStack(Blocks.planks, 6, 1))}, 10, 300));
		RecipeRegistry.registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log, 1, 2), new RecipeItem[]{new RecipeItem(new ItemStack(Blocks.planks, 6, 2))}, 10, 300));
		RecipeRegistry.registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log, 1, 3), new RecipeItem[]{new RecipeItem(new ItemStack(Blocks.planks, 6, 3))}, 10, 300));
		RecipeRegistry.registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log2, 1, 0), new RecipeItem[]{new RecipeItem(new ItemStack(Blocks.planks, 6, 4))}, 10, 300));
		RecipeRegistry.registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log2, 1, 1), new RecipeItem[]{new RecipeItem(new ItemStack(Blocks.planks, 6, 5))}, 10, 300));
	}
	
	public static void registerCentrifugeRecipes()
	{
		RecipeRegistry.registerRecipe(new RecipeCentrifuge(new RecipeItem(new OreStack("dustColumbite", 6)), new RecipeItem[]{ new RecipeItem(new ItemStack(MMItemManager.Dusts_Others.item(), 2, 1)), new RecipeItem(new ItemStack(MMItemManager.Dusts_Others.item(), 1, 2))}, 9, 560));
	}
	
	public static void registerPulverizerRecipes(){
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreCoal"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 0))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("blockObsidian"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 1))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreIron"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 2))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreGold"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 3))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreDiamond"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 4))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreCopper"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 5))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreTin"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 6))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreSilver"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 7))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreLead"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 8))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreNickel"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 9))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreRuby"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 2, 12))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreColumbite"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts_Others.item(), 2, 0))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreAluminum"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts_Others.item(), 2, 3))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreRedstone"),  new RecipeItem[]{new RecipeItem(new ItemStack(Items.redstone, 8))}, 15, 250));
		
		RecipeRegistry.registerRecipe(new RecipePulverizer(new ItemStack(Items.coal),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 0))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotIron"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 2))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotGold"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 3))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("gemDiamond"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 4))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotCopper"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 5))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotTin"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 6))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotSilver"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 7))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotLead"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 8))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotNickel"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 9))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotBronze"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 10))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotInvar"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 11))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotNiobium"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts_Others.item(), 1, 1))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotTantalum"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts_Others.item(), 1, 2))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("gemRuby"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Dusts.item(), 1, 12))}, 7, 125));
	}
	
	public static void registerAlloySmelterRecipes(){
		RecipeRegistry.registerRecipe(new RecipeAlloySmelter(new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)), new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Alloy_Ingots.item(), 4, 0))}, 9, 250));
		RecipeRegistry.registerRecipe(new RecipeAlloySmelter(new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)), new RecipeItem[]{new RecipeItem(new ItemStack(MMItemManager.Alloy_Ingots.item(), 3, 1))}, 9, 375));
	}
	
	public static void registerMetalRecipes()
	{
		GameRegistry.addShapedRecipe(new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 0), "+++", "+++", "+++", '+', new ItemStack(MMItemManager.Alloy_Nuggets.item(),1 , 0));
		GameRegistry.addShapedRecipe(new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 1), "+++", "+++", "+++", '+', new ItemStack(MMItemManager.Alloy_Nuggets.item(),1 , 1));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItemManager.Alloy_Nuggets.item(),9 , 0), new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItemManager.Alloy_Nuggets.item(),9 , 1), new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(MMItemManager.Ingots_Others.item(), 1, 0), "+++", "+++", "+++", '+', new ItemStack(MMItemManager.Nuggets_Others.item(),1 , 0));
		GameRegistry.addShapedRecipe(new ItemStack(MMItemManager.Ingots_Others.item(), 1, 1), "+++", "+++", "+++", '+', new ItemStack(MMItemManager.Nuggets_Others.item(),1 , 1));
		GameRegistry.addShapedRecipe(new ItemStack(MMItemManager.Ingots_Others.item(), 1, 2), "+++", "+++", "+++", '+', new ItemStack(MMItemManager.Nuggets_Others.item(),1 , 2));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItemManager.Nuggets_Others.item(),9 , 0), new ItemStack(MMItemManager.Ingots_Others.item(), 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItemManager.Nuggets_Others.item(),9 , 1), new ItemStack(MMItemManager.Ingots_Others.item(), 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItemManager.Nuggets_Others.item(),9 , 2), new ItemStack(MMItemManager.Ingots_Others.item(), 1, 2));
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 2), new ItemStack(Items.iron_ingot), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 3), new ItemStack(Items.gold_ingot), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 5), new ItemStack(NCItemManager.Ingots.item(), 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 6), new ItemStack(NCItemManager.Ingots.item(), 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 7), new ItemStack(NCItemManager.Ingots.item(), 1, 2), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 8), new ItemStack(NCItemManager.Ingots.item(), 1, 3), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 9), new ItemStack(NCItemManager.Ingots.item(), 1, 4), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 10), new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts.item(), 1, 11), new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts_Others.item(), 1, 1), new ItemStack(MMItemManager.Ingots_Others.item(), 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts_Others.item(), 1, 1), new ItemStack(MMItemManager.Ingots_Others.item(), 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItemManager.Dusts_Others.item(), 1, 3), new ItemStack(MMItemManager.Ingots_Others.item(), 1, 2), 0.5F);
		
	}
	
	public static void registerModularComponentRecipes(){
		IWorkbenchRecipe manager = ForestdayCrafting.workbenchRecipe;
		
		//if(!ModularConfig.pluginTinkers || !Loader.isModLoaded("TConstruct"))
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack plate = new ItemStack(MMItemManager.Component_Plates.item(), 1, i);
            ItemStack connection_wires = new ItemStack(MMItemManager.Component_Connection_Wires.item(), 4, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom){
            	MaterialManager.setMaterial(plate, mat);
            	MaterialManager.setMaterial(connection_wires, mat);
            	if (MaterialManager.getMaterial(plate) != null){
            		manager.addRecipe(new OreStack("ingot" + mat.getOreDict(), 2), new OreStack("toolHammer"), plate, 100);
            		manager.addRecipe(new OreStack("plate" + mat.getOreDict(), 1), new OreStack("toolCutter"), connection_wires, 100);
            	}
            }
        }
        //else
        	ItemStack plateIron = new ItemStack(MMItemManager.Component_Plates.item(), 1, 2);
        	ItemStack connection_wiresIron = new ItemStack(MMItemManager.Component_Connection_Wires.item(), 8, 2);
        	MaterialManager.setMaterial(plateIron, MMRegistry.Iron);
        	MaterialManager.setMaterial(connection_wiresIron, MMRegistry.Iron);
        	manager.addRecipe(new OreStack("ingotIron", 2), new OreStack("toolHammer"), plateIron, 100);
        	manager.addRecipe(new OreStack("plateIron", 1), new OreStack("toolCutter"), plateIron, 100);
           	ItemStack platePlastic = new ItemStack(MMItemManager.Component_Plates.item(), 2, 13);
        	MaterialManager.setMaterial(platePlastic, MMRegistry.Plastic);
        	manager.addRecipe(new OreStack("hardenedStarch", 1), new OreStack("toolHammer"), platePlastic, 100);
        	
        	ItemStack blockTin = new ItemStack(MMBlockManager.Metal_Blocks.item(), 1, 6);
        	MaterialManager.setMaterial(blockTin, MMRegistry.Tin);
            addShapedRecipe(blockTin, "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Ingots.item(), 1, 1));
            
        	ItemStack blockCopper = new ItemStack(MMBlockManager.Metal_Blocks.item(), 1, 7);
        	MaterialManager.setMaterial(blockCopper, MMRegistry.Copper);
            addShapedRecipe(blockCopper, "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Ingots.item()));
            
        	ItemStack blockBronze = new ItemStack(MMBlockManager.Metal_Blocks.item(), 1, 8);
        	MaterialManager.setMaterial(blockBronze, MMRegistry.Bronze);
            addShapedRecipe(blockBronze, "+++", "+++", "+++", '+', new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 0));
            
        	ItemStack blockSteel = new ItemStack(MMBlockManager.Metal_Blocks.item(), 1, 9);
        	MaterialManager.setMaterial(blockSteel, MMRegistry.Steel);
            addShapedRecipe(blockSteel, "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Ingots.item()));
            
        	ItemStack blockNiobium = new ItemStack(MMBlockManager.Metal_Blocks.item(), 1, 10);
        	MaterialManager.setMaterial(blockNiobium, MMRegistry.Niobium);
            addShapedRecipe(blockNiobium, "+++", "+++", "+++", '+', new ItemStack(MMItemManager.Ingots_Others.item(), 1, 0));
            
        	ItemStack blockTantalum = new ItemStack(MMBlockManager.Metal_Blocks.item(), 1, 11);
        	MaterialManager.setMaterial(blockTantalum, MMRegistry.Tantalum);
            addShapedRecipe(blockTantalum, "+++", "+++", "+++", '+', new ItemStack(MMItemManager.Ingots_Others.item(), 1, 1));
            
        	ItemStack blockSilver = new ItemStack(MMBlockManager.Metal_Blocks.item(), 1, 12);
        	MaterialManager.setMaterial(blockSilver, MMRegistry.Silver);
            addShapedRecipe(blockSilver, "+++", "+++", "+++", '+', new ItemStack(NCItemManager.Ingots.item(), 1, 2));
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
