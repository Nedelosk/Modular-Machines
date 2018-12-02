package modularmachines.api.ingredients;

import java.util.Collection;

public interface IIngredientHolder<I> {
	
	Collection<I> getIngredients();
}
