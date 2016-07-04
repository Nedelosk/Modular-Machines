package de.nedelosk.modularmachines.common.recipse;

import java.util.Map;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.property.PropertyRecipeItems;
import de.nedelosk.modularmachines.api.property.PropertyString;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.nbt.NBTBase;

public class Recipe implements IRecipe{

	public static final PropertyRecipeItems INPUTS = new PropertyRecipeItems("inputs");
	public static final PropertyRecipeItems OUTPUTS = new PropertyRecipeItems("outputs");
	public static final PropertyString NAME = new PropertyString("name", null);
	public static final PropertyString CATEGORY = new PropertyString("category", null);
	
	protected Map<IProperty, Object> properties;
	
	public Recipe(Map<IProperty, Object> properties) {
		this.properties = properties;
	}
	
	@Override
	public Map<IProperty, Object> getProperties() {
		return properties;
	}

	@Override
	public <T> T get(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property) {
		if(!properties.containsKey(property)){
			throw new IllegalArgumentException("Cannot get property " + property + " as it is not registred in the recipe.");
		}
		return (T) properties.get(property);
	}

	@Override
	public RecipeItem[] getInputs() {
		return get(INPUTS);
	}

	@Override
	public RecipeItem[] getOutputs() {
		return get(OUTPUTS);
	}

	@Override
	public String getRecipeName() {
		return get(NAME);
	}

	@Override
	public String getRecipeCategory() {
		return get(CATEGORY);
	}
}
