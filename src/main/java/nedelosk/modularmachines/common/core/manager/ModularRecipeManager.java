package nedelosk.modularmachines.common.core.manager;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.common.managers.CraftingManager;
import nedelosk.forestday.common.registrys.FItems;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.crafting.IModularCraftingRecipe;
import nedelosk.modularmachines.api.modular.crafting.ShapedModularCraftingRecipe;
import nedelosk.modularmachines.api.modular.module.recipes.RecipeItem;
import nedelosk.modularmachines.api.modular.module.recipes.RecipeRegistry;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.modular.module.tool.producer.alloysmelter.RecipeAlloySmelter;
import nedelosk.modularmachines.common.modular.module.tool.producer.centrifuge.RecipeCentrifuge;
import nedelosk.modularmachines.common.modular.module.tool.producer.pulverizer.RecipePulverizer;
import nedelosk.modularmachines.common.modular.module.tool.producer.sawmill.RecipeSawMill;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import nedelosk.nedeloskcore.common.core.registry.ObjectRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModularRecipeManager {

	public static void preInit()
	{
		registerSawMillRecipes();
		registerPulverizerRecipes();
		registerAlloySmelterRecipes();
		registerCentrifugeRecipes();
		registerMetalRecipes();
		registerModuleRecipes();
	}
	
	public static void registerModuleRecipes()
	{
		ModularMachinesApi.registerRecipe(new ShapedModularCraftingRecipe(new String[]{"MODULE.BASE"}, new ItemStack(MMItems.Module_Items.item(), 1, 3), 0, " --- ", "-+++-", "-+++-", "-+++-", " --- ", '+', new ItemStack(MMItems.Plates.item(), 1, 8), '-', "nuggetIron"));
		ModularMachinesApi.registerRecipe(new ShapedModularCraftingRecipe(new String[]{"CAPACITOR.BASE"}, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0), 0, " --- ", "-PRP-", "-PRP-", " --- ", " i i ", 'R', Items.redstone, 'P', new ItemStack(MMItems.Plates.item(), 1, 11), '-', new ItemStack(MMItems.Plates.item(), 1, 8), 'i', "nuggetIron"));
		ModularMachinesApi.registerRecipe(new ShapedModularCraftingRecipe(new String[]{"CAPACITOR.IMPROVED"}, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 1), 0, " --- ", "-PRP-", "-PRP-", " --- ", " C C ", 'R', Items.redstone, 'P', new ItemStack(MMItems.Plates.item(), 1, 9), '-', new ItemStack(MMItems.Plates.item(), 1, 8), 'C', new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0)));
		ModularMachinesApi.registerRecipe(new ShapedModularCraftingRecipe(new String[]{"CAPACITOR.IMPROVED"}, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 2), 0, " --- ", "-PRP-", "-PRP-", " --- ", " C C ", 'R', Items.redstone, 'P', new ItemStack(MMItems.Plates.item(), 1, 10), '-', new ItemStack(MMItems.Plates.item(), 1, 8), 'C', new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0)));
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
		RecipeRegistry.registerRecipe(new RecipeCentrifuge(new RecipeItem(new OreStack("dustColumbite", 6)), new RecipeItem[]{ new RecipeItem(new ItemStack(MMItems.Dusts_Others.item(), 2, 1)), new RecipeItem(new ItemStack(MMItems.Dusts_Others.item(), 1, 2))}, 9, 560));
	}
	
	public static void registerPulverizerRecipes(){
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreCoal"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 0))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("blockObsidian"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 1))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreIron"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 2))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreGold"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 3))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreDiamond"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 4))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreCopper"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 5))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreTin"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 6))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreSilver"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 7))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreLead"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 8))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreNickel"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 9))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreRuby"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 2, 12))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreColumbite"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts_Others.item(), 2, 0))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreAluminum"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts_Others.item(), 2, 3))}, 15, 250));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("oreRedstone"),  new RecipeItem[]{new RecipeItem(new ItemStack(Items.redstone, 8))}, 15, 250));
		
		RecipeRegistry.registerRecipe(new RecipePulverizer(new ItemStack(Items.coal),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 0))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotIron"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 2))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotGold"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 3))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("gemDiamond"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 4))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotCopper"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 5))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotTin"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 6))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotSilver"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 7))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotLead"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 8))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotNickel"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 9))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotBronze"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 10))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotInvar"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 11))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotNiobium"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts_Others.item(), 1, 1))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("ingotTantalum"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts_Others.item(), 1, 2))}, 7, 125));
		RecipeRegistry.registerRecipe(new RecipePulverizer(new OreStack("gemRuby"),  new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Dusts.item(), 1, 12))}, 7, 125));
	}
	
	public static void registerAlloySmelterRecipes(){
		RecipeRegistry.registerRecipe(new RecipeAlloySmelter(new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)), new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Alloy_Ingots.item(), 4, 0))}, 9, 250));
		RecipeRegistry.registerRecipe(new RecipeAlloySmelter(new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)), new RecipeItem[]{new RecipeItem(new ItemStack(MMItems.Alloy_Ingots.item(), 3, 1))}, 9, 375));
	}
	
	public static void registerMetalRecipes()
	{
		CraftingManager.addShapedRecipe(new ItemStack(MMItems.Plates.item(), 4, 8), "+++", "+++", "+++", '+', new ItemStack(FItems.nature.item(), 1, 11));
		GameRegistry.addShapedRecipe(new ItemStack(MMItems.Alloy_Ingots.item(), 1, 0), "+++", "+++", "+++", '+', new ItemStack(MMItems.Alloy_Nuggets.item(),1 , 0));
		GameRegistry.addShapedRecipe(new ItemStack(MMItems.Alloy_Ingots.item(), 1, 1), "+++", "+++", "+++", '+', new ItemStack(MMItems.Alloy_Nuggets.item(),1 , 1));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItems.Alloy_Nuggets.item(),9 , 0), new ItemStack(MMItems.Alloy_Ingots.item(), 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItems.Alloy_Nuggets.item(),9 , 1), new ItemStack(MMItems.Alloy_Ingots.item(), 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(MMItems.Ingots_Others.item(), 1, 0), "+++", "+++", "+++", '+', new ItemStack(MMItems.Nuggets_Others.item(),1 , 0));
		GameRegistry.addShapedRecipe(new ItemStack(MMItems.Ingots_Others.item(), 1, 1), "+++", "+++", "+++", '+', new ItemStack(MMItems.Nuggets_Others.item(),1 , 1));
		GameRegistry.addShapedRecipe(new ItemStack(MMItems.Ingots_Others.item(), 1, 2), "+++", "+++", "+++", '+', new ItemStack(MMItems.Nuggets_Others.item(),1 , 2));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItems.Nuggets_Others.item(),9 , 0), new ItemStack(MMItems.Ingots_Others.item(), 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItems.Nuggets_Others.item(),9 , 1), new ItemStack(MMItems.Ingots_Others.item(), 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(MMItems.Nuggets_Others.item(),9 , 2), new ItemStack(MMItems.Ingots_Others.item(), 1, 2));
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 2), new ItemStack(Items.iron_ingot), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 3), new ItemStack(Items.gold_ingot), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 5), new ItemStack(ObjectRegistry.ingots, 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 6), new ItemStack(ObjectRegistry.ingots, 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 7), new ItemStack(ObjectRegistry.ingots, 1, 2), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 8), new ItemStack(ObjectRegistry.ingots, 1, 3), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 9), new ItemStack(ObjectRegistry.ingots, 1, 4), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 10), new ItemStack(MMItems.Alloy_Ingots.item(), 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts.item(), 1, 11), new ItemStack(MMItems.Alloy_Ingots.item(), 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts_Others.item(), 1, 1), new ItemStack(MMItems.Ingots_Others.item(), 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts_Others.item(), 1, 2), new ItemStack(MMItems.Ingots_Others.item(), 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(MMItems.Dusts_Others.item(), 1, 3), new ItemStack(MMItems.Ingots_Others.item(), 1, 2), 0.5F);
		
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 0), "toolHammer", "ingotIron", "ingotIron");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 1), "toolHammer", "ingotGold", "ingotGold");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 2), "toolHammer", "ingotCopper", "ingotCopper");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 3), "toolHammer", "ingotTin", "ingotTin");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 4), "toolHammer", "ingotSilver", "ingotSilver");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 5), "toolHammer", "ingotLead", "ingotLead");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 6), "toolHammer", "ingotBronze", "ingotBronze");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 7), "toolHammer", "ingotInvar", "ingotInvar");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 9), "toolHammer", "ingotNiobium", "ingotNiobium");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 10), "toolHammer", "ingotTantalum", "ingotTantalum");
		addShapelessRecipe(new ItemStack(MMItems.Plates.item(), 1, 11), "toolHammer", "ingotAluminium", "ingotAluminium");
	}
	
	public static ItemStack findMatchingModularRecipe(IInventory inventory, EntityPlayer player)
	{
	     IModularCraftingRecipe recipe = null;
	     for (Object var11 : ModularMachinesApi.getModularRecipes()) {
	       if (((var11 instanceof IModularCraftingRecipe)) && (((IModularCraftingRecipe)var11).matches(inventory, player.worldObj, player)))
	       {
	         recipe = (IModularCraftingRecipe)var11;
	         break;
	       }
	     }
	     return recipe == null ? null : recipe.getCraftingResult(inventory);
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
