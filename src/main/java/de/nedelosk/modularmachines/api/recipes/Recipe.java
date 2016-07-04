package de.nedelosk.modularmachines.api.recipes;

import java.util.Map;
import java.util.Map.Entry;

import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.api.property.PropertyRecipeItems;
import de.nedelosk.modularmachines.api.property.PropertyString;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class Recipe implements IRecipe{

	public static final PropertyRecipeItems INPUTS = new PropertyRecipeItems("inputs");
	public static final PropertyRecipeItems OUTPUTS = new PropertyRecipeItems("outputs");
	public static final PropertyString NAME = new PropertyString("name", null);
	public static final PropertyString CATEGORY = new PropertyString("category", null);
	public static final PropertyInteger SPEED = new PropertyInteger("speed", 0);
	public static final PropertyInteger HEAT = new PropertyInteger("heat", 0);

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
		return get(Recipe.INPUTS);
	}

	@Override
	public RecipeItem[] getOutputs() {
		return get(Recipe.OUTPUTS);
	}

	@Override
	public String getRecipeName() {
		return get(Recipe.NAME);
	}

	@Override
	public String getRecipeCategory() {
		return get(Recipe.CATEGORY);
	}

	@Override
	public int getSpeed() {
		return get(Recipe.SPEED);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		for(Entry<IProperty, Object> object : properties.entrySet()){
			if(object.getValue() != null){
				nbt.setTag(object.getKey().getName(), object.getKey().writeToNBT(this, object.getValue()));
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		for(IProperty property : properties.keySet()){
			if(nbt.hasKey(property.getName())){
				properties.put(property, property.readFromNBT(nbt.getTag(property.getName()), this));
			}
		}
	}
}
