package de.nedelosk.forestmods.library.utils;

import static de.nedelosk.forestmods.library.recipes.RecipeRegistry.registerRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.nedelosk.forestmods.library.recipes.IRecipeInventory;
import de.nedelosk.forestmods.library.recipes.Recipe;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.ChestGenHooks;

public class RecipeUtil {

	public static void removeShapedRecipes(List<ItemStack> removelist) {
		for(ItemStack stack : removelist) {
			removeShapedRecipe(stack);
		}
	}

	public static void removeAnyRecipe(ItemStack resultItem) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for(int i = 0; i < recipes.size(); i++) {
			IRecipe tmpRecipe = recipes.get(i);
			ItemStack recipeResult = tmpRecipe.getRecipeOutput();
			if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
				recipes.remove(i--);
			}
		}
	}

	public static void removeShapedRecipe(ItemStack resultItem) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for(int i = 0; i < recipes.size(); i++) {
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes) tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();
				if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
					recipes.remove(i--);
				}
			}
		}
	}

	public static void removeShapelessRecipe(ItemStack resultItem) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for(int i = 0; i < recipes.size(); i++) {
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof ShapelessRecipes) {
				ShapelessRecipes recipe = (ShapelessRecipes) tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();
				if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
					recipes.remove(i--);
				}
			}
		}
	}

	public static void removeFurnaceRecipe(ItemStack resultItem) {
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
		Iterator<Entry<ItemStack, ItemStack>> i = recipes.entrySet().iterator();
		while (i.hasNext()) {
			Entry<ItemStack, ItemStack> entry = i.next();
			if (entry.getValue().getItem() == resultItem.getItem() && entry.getValue().getItemDamage() == resultItem.getItemDamage()) {
				i.remove();
			}
		}
	}

	public static void removeFurnaceRecipe(Item i, int metadata) {
		removeFurnaceRecipe(new ItemStack(i, 1, metadata));
	}

	public static void removeFurnaceRecipe(Item i) {
		removeFurnaceRecipe(new ItemStack(i));
	}

	// removes from all vanilla worldgen chests :D
	public static void removeFromChests(ItemStack resultItem) {
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).removeItem(resultItem);
	}

	public static boolean addAlloySmelter(String recipeName, RecipeItem input1, RecipeItem input2, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input1, input2 }, output, speedModifier, energy, "AlloySmelter"));
	}

	public static boolean addPulverizer(String recipeName, ItemStack input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "Pulverizer"));
	}

	public static boolean addPulverizer(String recipeName, OreStack input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "Pulverizer"));
	}

	public static boolean addPulverizer(String recipeName, RecipeItem input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input }, output, speedModifier, energy, "Pulverizer"));
	}

	public static boolean addSawMill(String recipeName, OreStack input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "SawMill"));
	}

	public static boolean addSawMill(String recipeName, ItemStack input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "SawMill"));
	}

	public static boolean addSawMill(String recipeName, RecipeItem input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input }, output, speedModifier, energy, "SawMill"));
	}

	public static boolean addBoiler(String recipeName, RecipeItem input, RecipeItem output, int speedModifier, int heat){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input }, new RecipeItem[] { output }, speedModifier, heat, "Boiler"));
	}

	public static boolean canRemoveRecipeInputs(IRecipeInventory inventory, int chance, RecipeItem[] inputs) {
		if (inputs != null) {
			for(RecipeItem recipeInput : inputs) {
				if (recipeInput != null) {
					int stackSize = 0;
					if (recipeInput.isOre()) {
						stackSize = recipeInput.ore.stackSize;
					} else if (recipeInput.isItem()) {
						stackSize = recipeInput.item.stackSize;
					} else {
						continue;
					}
					ItemStack itemStack = inventory.getStackInSlot(recipeInput.index);
					if (itemStack.stackSize < stackSize) {
						return false;
					}
					continue;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static void removeRecipeInputs(IRecipeInventory inventory, int chance, RecipeItem[] inputs){
		if (inputs != null) {
			for(RecipeItem recipeInput : inputs) {
				if (recipeInput != null) {
					if (recipeInput.isOre()) {
						inventory.decrStackSize(recipeInput.index, recipeInput.ore.stackSize);
					} else if (recipeInput.isItem()) {
						inventory.decrStackSize(recipeInput.index, recipeInput.item.stackSize);
					}
				}
			}
		}
	}

	public static boolean canAddRecipeOutputs(IRecipeInventory inventory, int chance, RecipeItem[] outputs) {
		List<ItemStack> outputStacks = new ArrayList<ItemStack>(inventory.getOutputs());
		if(inventory.getOutputs() > 0) {
			boolean allFull = true;
			for (int i = inventory.getInputs(); i < inventory.getInputs() + inventory.getOutputs(); i++) {
				ItemStack st = inventory.getStackInSlot(i);
				if(st != null) {
					st = st.copy();
					if(allFull && st.stackSize < st.getMaxStackSize()) {
						allFull = false;
					}
				} else {
					allFull = false;
				}
				outputStacks.add(st);
			}
			if(allFull) {
				return false;
			}
		}

		for (RecipeItem result : outputs) {
			if(result.item != null) {
				if(mergeItemResult(result.item, outputStacks) == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public static void addRecipeOutputs(IRecipeInventory inventory, int chance, RecipeItem[] outputs) {
		List<ItemStack> outputStacks = new ArrayList<ItemStack>(inventory.getOutputs());
		if(inventory.getOutputs() > 0) {
			for (int i = inventory.getInputs(); i < inventory.getInputs() + inventory.getOutputs(); i++) {
				ItemStack it = inventory.getStackInSlot(i);
				if(it != null) {
					it = it.copy();
				}
				outputStacks.add(it);
			}
		}

		for (RecipeItem result : outputs) {
			if(result.item != null) {
				int numMerged = mergeItemResult(result.item, outputStacks);
				if(numMerged > 0) {
					result.item.stackSize -= numMerged;
				}
			}
		}

		if(inventory.getOutputs() > 0) {
			int listIndex = 0;
			for (int i = inventory.getInputs(); i < inventory.getInputs() + inventory.getOutputs(); i++) {
				ItemStack st = outputStacks.get(listIndex);
				if(st != null) {
					st = st.copy();
				}
				inventory.setInventorySlotContents(i, st);
				listIndex++;
			}
		}
	}

	private static int mergeItemResult(ItemStack item, List<ItemStack> outputStacks) {
		int res = 0;

		ItemStack copy = item.copy();

		for (ItemStack outStack : outputStacks) {
			if(outStack != null && copy != null) {
				int num = getNumCanMerge(outStack, copy);
				outStack.stackSize += num;
				res += num;
				copy.stackSize -= num;
				if(copy.stackSize <= 0) {
					return item.stackSize;
				}
			}
		}

		for (int i = 0; i < outputStacks.size(); i++) {
			ItemStack outStack = outputStacks.get(i);
			if(outStack == null) {
				outputStacks.set(i, copy);
				return item.stackSize;
			}
		}

		return 0;
	}

	private static int getNumCanMerge(ItemStack itemStack, ItemStack result) {
		if(!itemStack.isItemEqual(result)) {
			return 0;
		}
		return Math.min(itemStack.getMaxStackSize() - itemStack.stackSize, result.stackSize);
	}
}