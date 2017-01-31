package modularmachines.common.modules.filters;

/*public class FilterMachine implements IContentFilter<Object, IModuleMachine> {

	public static final FilterMachine INSTANCE = new FilterMachine();

	private FilterMachine() {
	}

	@Override
	public boolean isValid(int index, Object content, IModuleState<IModuleMachine> state) {
		if (content == null) {
			return false;
		}
		RecipeItem recipeItem = null;
		if (content instanceof ItemStack) {
			recipeItem = new RecipeItem(index, (ItemStack) content);
		} else if (content instanceof FluidStack) {
			recipeItem = new RecipeItem(index, (FluidStack) content);
		} else {
			return false;
		}
		return state.getModule().isRecipeInput(state, recipeItem);
	}
}*/
