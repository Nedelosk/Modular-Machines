package modularmachines.common.recipse;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTBase;

import modularmachines.api.property.IProperty;
import modularmachines.api.property.IPropertyJson;
import modularmachines.api.property.IPropertyProvider;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.IRecipeBuilder;
import modularmachines.api.recipes.Recipe;

public class RecipeBuilder implements IRecipeBuilder {

	protected final Map<IProperty, Object> properties;

	public RecipeBuilder() {
		this.properties = new HashMap<>();
	}

	@Override
	public <T, V extends T> IRecipeBuilder set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value) {
		if (property instanceof IPropertyJson) {
			properties.put(property, value);
		}
		return this;
	}

	@Override
	public IRecipe build() {
		return new Recipe(properties);
	}
}
