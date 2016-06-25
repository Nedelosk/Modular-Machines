package de.nedelosk.modularmachines.common.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleRecipeWrapper implements IRecipeWrapper {

	protected IRecipe recipe;

	public ModuleRecipeWrapper(IRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public List getInputs() {
		List inputs = new ArrayList<>();
		for(RecipeItem item : recipe.getInputs()){
			if(item != null && !item.isNull()){
				if(item.isItem()){
					inputs.add(item.item);
				}else if(item.isOre()){
					List oreStacks = new ArrayList<>();
					List<ItemStack> stacks = OreDictionary.getOres(item.ore.oreDict);
					for(ItemStack stack : stacks){
						ItemStack newStack = stack.copy();
						newStack.stackSize = item.ore.stackSize;
						oreStacks.add(newStack);
					}
					inputs.add(oreStacks);
				}
			}
		}
		return inputs;
	}

	@Override
	public List getOutputs() {
		List inputs = new ArrayList<>();
		for(RecipeItem item : recipe.getOutputs()){
			if(item != null && !item.isNull()){
				if(item.isItem()){
					inputs.add(item.item);
				}
			}
		}
		return inputs;
	}

	@Override
	public List<FluidStack> getFluidInputs() {
		List inputs = new ArrayList<>();
		for(RecipeItem item : recipe.getInputs()){
			if(item != null && !item.isNull()){
				if(item.isFluid()){
					inputs.add(item.fluid);
				}
			}
		}
		return inputs;
	}

	@Override
	public List<FluidStack> getFluidOutputs() {
		List inputs = new ArrayList<>();
		for(RecipeItem item : recipe.getOutputs()){
			if(item != null && !item.isNull()){
				if(item.isFluid()){
					inputs.add(item.fluid);
				}
			}
		}
		return inputs;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
	}

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return null;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}
}
