package de.nedelosk.modularmachines.common.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleRecipeWrapper extends BlankRecipeWrapper implements IRecipeWrapper {

	protected IRecipe recipe;
	protected String recipeCategoryUid;

	public ModuleRecipeWrapper(IRecipe recipe, String recipeCategoryUid) {
		this.recipe = recipe;
		this.recipeCategoryUid = recipeCategoryUid;
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

	public static List<ModuleRecipeWrapper> getRecipes(String recipeCategory, String recipeCategoryUid, Class<? extends ModuleRecipeWrapper> wrapper) {
		List<ModuleRecipeWrapper> recipes = new ArrayList<>();
		for (IRecipe recipe : RecipeRegistry.getRecipeHandler(recipeCategory).getRecipes()) {
			try{
				recipes.add(wrapper.getConstructor(IRecipe.class, String.class).newInstance(recipe, recipeCategoryUid));
			}catch(Exception e){

			}
		}
		return recipes;
	}
}
