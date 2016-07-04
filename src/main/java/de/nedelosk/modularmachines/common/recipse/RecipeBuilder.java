package de.nedelosk.modularmachines.common.recipse;

import java.util.HashMap;
import java.util.Map;

import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyJson;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipeBuilder;
import de.nedelosk.modularmachines.api.recipes.Recipe;
import net.minecraft.nbt.NBTBase;

public class RecipeBuilder implements IRecipeBuilder{

	protected final Map<IProperty, Object> properties;

	public RecipeBuilder() {
		this.properties = new HashMap<>();
	}

	@Override
	public <T, V extends T> IRecipeBuilder set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value) {
		if(property instanceof IPropertyJson){
			properties.put(property, value);
		}
		return this;
	}

	@Override
	public IRecipe build() {
		return new Recipe(properties);
	}
}
