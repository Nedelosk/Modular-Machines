package de.nedelosk.forestmods.api.recipes;

public class Recipe implements IRecipe {

	protected final Object[] modifiers;
	protected final RecipeItem[] input;
	protected final RecipeItem[] output;
	protected final int speedModifier;
	protected final int material;
	protected final String recipeCategory;
	protected final String recipeName;

	public Recipe(String recipeName, RecipeItem[] input, RecipeItem[] output, int speedModifier, int material, String recipeCategory, Object... modifiers) {
		this.recipeName = recipeName;
		this.input = input;
		this.output = output;
		this.modifiers = modifiers;
		this.speedModifier = speedModifier;
		this.material = material;
		this.recipeCategory = recipeCategory;
	}

	@Override
	public int getRequiredSpeedModifier() {
		return speedModifier;
	}

	@Override
	public Object[] getModifiers() {
		return modifiers;
	}

	@Override
	public Object getModifier(int modifierID) {
		return getModifiers()[modifierID];
	}

	@Override
	public String getRecipeCategory() {
		return recipeCategory;
	}

	@Override
	public String getRecipeName() {
		return recipeName;
	}

	@Override
	public RecipeItem[] getInputs() {
		return input;
	}

	@Override
	public RecipeItem[] getOutputs() {
		return output;
	}

	@Override
	public int getRequiredMaterial() {
		return material;
	}

	@Override
	public boolean matches(Object[] craftingModifiers) {
		return true;
	}
}
