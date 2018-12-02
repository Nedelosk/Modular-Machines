package modularmachines.api.ingredients;

import javax.annotation.Nullable;

public interface IIngredientConsumer<I> extends IIngredientHandler {
	@Nullable
	I injectIngredient(I ingredient, boolean simulate);
}
