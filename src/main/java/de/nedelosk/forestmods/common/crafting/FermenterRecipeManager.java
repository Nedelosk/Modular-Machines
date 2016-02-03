package de.nedelosk.forestmods.common.crafting;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestmods.api.multiblocks.IFermenterRecipe;
import net.minecraftforge.fluids.FluidStack;

public class FermenterRecipeManager implements IFermenterRecipe {

	private static ArrayList<FermenterRecipe> recipes = new ArrayList();
	public static FermenterRecipeManager instance;

	public static void addRecipe(FermenterRecipe recipe) {
		recipes.add(recipe);
	}

	public static void removeRecipe(FermenterRecipe recipe) {
		recipes.remove(recipe);
	}

	public static void addAllRecipes(List<FermenterRecipe> recipe) {
		recipes.addAll(recipe);
	}

	@Override
	public void addRecipe(int burnTime, FluidStack input, FluidStack output) {
		recipes.add(new FermenterRecipe(burnTime, input, output));
	}

	public static boolean isFluidInput(FluidStack stack) {
		for ( FermenterRecipe sr : FermenterRecipeManager.recipes ) {
			if (sr.getInput().getFluid() == stack.getFluid()) {
				return true;
			}
		}
		return false;
	}

	public static FermenterRecipe getRecipe(FluidStack stack) {
		for ( FermenterRecipe sr : FermenterRecipeManager.recipes ) {
			FluidStack stackInput = sr.getInput();
			if (stackInput.getFluid() == stack.getFluid() && stackInput.amount <= stack.amount) {
				return sr;
			}
		}
		return null;
	}

	public static ArrayList<FermenterRecipe> getRecipes() {
		return recipes;
	}

	public static FermenterRecipeManager getInstance() {
		return instance;
	}

	public class FermenterRecipe {

		private FluidStack input;
		private FluidStack output;
		private int burntTime;

		public FermenterRecipe(int burnTime, FluidStack input, FluidStack output) {
			this.input = input;
			this.output = output;
			this.burntTime = burnTime;
		}

		public int getBurntTime() {
			return burntTime;
		}

		public FluidStack getInput() {
			return input;
		}

		public FluidStack getOutput() {
			return output;
		}
	}
}