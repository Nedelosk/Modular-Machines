package de.nedelosk.forestmods.api.recipes;

public class RecipeWithMode extends Recipe {

	public RecipeWithMode(RecipeItem[] input, RecipeItem[] output, int speedModifier, int material, String recipeName, Object[] modifiers) {
		super(input, output, speedModifier, material, recipeName, modifiers);
	}

	@Override
	public boolean matches(Object[] craftingModifiers) {
		if (craftingModifiers == null || craftingModifiers.length == 0) {
			return false;
		}
		if (craftingModifiers[0] instanceof IMachineMode) {
			IMachineMode mode = (IMachineMode) craftingModifiers[0];
			if (mode == this.modifiers[0]) {
				return true;
			}
		}
		return false;
	}
}
