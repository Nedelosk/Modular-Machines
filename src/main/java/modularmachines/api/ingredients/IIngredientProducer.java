package modularmachines.api.ingredients;

import javax.annotation.Nullable;

public interface IIngredientProducer<I> extends IIngredientHandler {
	@Nullable
	I extractIngredient(I ingredient, boolean simulate);
}
