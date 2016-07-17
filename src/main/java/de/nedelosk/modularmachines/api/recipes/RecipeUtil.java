package de.nedelosk.modularmachines.api.recipes;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.property.PropertyToolMode;
import de.nedelosk.modularmachines.common.modules.storaged.tools.ModuleLathe.LatheModes;
import net.minecraft.item.ItemStack;

public class RecipeUtil {

	public static final PropertyToolMode LATHEMODE = new PropertyToolMode("mode", LatheModes.class, LatheModes.ROD);

	public static boolean addPulverizer(String recipeName, ItemStack input, RecipeItem[] output, int speed){
		return addPulverizer(recipeName, new RecipeItem(input), output, speed);
	}

	public static boolean addPulverizer(String recipeName, OreStack input, RecipeItem[] output, int speed){
		return addPulverizer(recipeName, new RecipeItem(input), output, speed);
	}

	public static boolean addPulverizer(String recipeName, RecipeItem input, RecipeItem[] output, int speed){
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("Pulverizer");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder
		.set(Recipe.INPUTS, new RecipeItem[]{input}).
		set(Recipe.OUTPUTS, output)
		.set(Recipe.SPEED, speed);
		return handler.registerRecipe(builder.build());
	}


	public static boolean addAlloySmelter(String recipeName, RecipeItem inputFirst, RecipeItem inputSecond, RecipeItem[] output, int speed, double heat, double heatToRemove){
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("AlloySmelter");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder
		.set(Recipe.INPUTS, new RecipeItem[]{inputFirst, inputSecond}).
		set(Recipe.OUTPUTS, output)
		.set(Recipe.SPEED, speed)
		.set(Recipe.HEAT, heat)
		.set(Recipe.HEATTOREMOVE, heatToRemove);
		return handler.registerRecipe(builder.build());
	}

	public static boolean addLathe(String recipeName, RecipeItem input, RecipeItem output, int speed, LatheModes mode){
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("Lathe");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder
		.set(Recipe.INPUTS, new RecipeItem[]{input}).
		set(Recipe.OUTPUTS, new RecipeItem[]{output})
		.set(Recipe.SPEED, speed)
		.set(LATHEMODE, mode);
		return handler.registerRecipe(builder.build());
	}

	/*public static boolean addSawMill(String recipeName, OreStack input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "SawMill"));
	}

	public static boolean addSawMill(String recipeName, ItemStack input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { new RecipeItem(input) }, output, speedModifier, energy, "SawMill"));
	}

	public static boolean addSawMill(String recipeName, RecipeItem input, RecipeItem[] output, int speedModifier, int energy){
		return registerRecipe(new Recipe(recipeName, new RecipeItem[] { input }, output, speedModifier, energy, "SawMill"));
	}*/

	public static boolean addBoilerRecipe(String recipeName, RecipeItem input, RecipeItem output, int speed, double heat, double heatToRemove){
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler("Boiler");
		IRecipeBuilder builder = handler.getDefaultTemplate();
		builder
		.set(Recipe.INPUTS, new RecipeItem[]{input}).
		set(Recipe.OUTPUTS, new RecipeItem[]{output})
		.set(Recipe.SPEED, speed)
		.set(Recipe.HEAT, heat)
		.set(Recipe.HEATTOREMOVE, heatToRemove);
		return handler.registerRecipe(builder.build());
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
						inventory.extractItem(recipeInput.index, recipeInput.ore.stackSize, false);
					} else if (recipeInput.isItem()) {
						inventory.extractItem(recipeInput.index, recipeInput.item.stackSize, false);
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
				inventory.insertItem(i, st, false);
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