package de.nedelosk.modularmachines.common.recipse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyBuilder;
import de.nedelosk.modularmachines.api.property.IPropertyJson;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipeBuilder;
import de.nedelosk.modularmachines.api.recipes.IRecipeHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

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
