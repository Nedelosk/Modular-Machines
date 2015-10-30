package nedelosk.modularmachines.api.recipes;

public class Recipe implements IRecipe {

	protected int[] modifiers;
	protected final RecipeItem[] input;
	protected final RecipeItem[] output;
	protected int speedModifier;
	protected int material;
	protected String recipeName;

	public Recipe(RecipeItem[] input, RecipeItem[] output, int speedModifier, int material, String recipeName,
			int... modifiers) {
		this.input = input;
		this.output = output;
		this.modifiers = modifiers;
		this.speedModifier = speedModifier;
		this.material = material;
		this.recipeName = recipeName;
	}

	@Override
	public int getRequiredSpeedModifier() {
		return speedModifier;
	}

	@Override
	public int[] getModifiers() {
		return modifiers;
	}

	@Override
	public int getModifier(int modifierID) {
		return getModifiers()[modifierID];
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

}
