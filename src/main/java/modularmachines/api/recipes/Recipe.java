package modularmachines.api.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.property.IProperty;
import modularmachines.api.property.IPropertyJson;
import modularmachines.api.property.IPropertyProvider;
import modularmachines.api.property.PropertyDouble;
import modularmachines.api.property.PropertyInteger;
import modularmachines.api.property.PropertyRecipeItems;
import modularmachines.api.property.PropertyString;

public class Recipe implements IRecipe {

	public static final PropertyRecipeItems INPUTS = new PropertyRecipeItems("inputs");
	public static final PropertyRecipeItems OUTPUTS = new PropertyRecipeItems("outputs");
	public static final PropertyString NAME = new PropertyString("name", null);
	public static final PropertyString CATEGORY = new PropertyString("category", null);
	public static final PropertyInteger SPEED = new PropertyInteger("speed", 0);
	public static final PropertyDouble HEAT = new PropertyDouble("heat", 0);
	public static final PropertyDouble HEATTOREMOVE = new PropertyDouble("removeHeat", 0);
	public static final PropertyDouble KINETIC = new PropertyDouble("kinetic", 0);
	protected Map<IProperty, Object> properties;

	public Recipe(Map<IProperty, Object> properties) {
		this.properties = properties;
	}

	@Override
	public Map<IProperty, Object> getProperties() {
		return properties;
	}

	@Override
	public <T> T getValue(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property) {
		if (!properties.containsKey(property)) {
			throw new IllegalArgumentException("Cannot get property " + property + " as it is not registred in the recipe.");
		}
		return (T) properties.get(property);
	}

	@Override
	public RecipeItem[] getInputs() {
		return getValue(Recipe.INPUTS);
	}

	@Override
	public RecipeItem[] getOutputs() {
		return getValue(Recipe.OUTPUTS);
	}

	@Override
	public String getRecipeName() {
		return getValue(Recipe.NAME);
	}

	@Override
	public String getRecipeCategory() {
		return getValue(Recipe.CATEGORY);
	}

	@Override
	public int getSpeed() {
		return getValue(Recipe.SPEED);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		for (Entry<IProperty, Object> object : properties.entrySet()) {
			if (object.getValue() != null) {
				nbt.setTag(object.getKey().getName(), object.getKey().writeToNBT(this, object.getValue()));
			}
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		for (IProperty property : properties.keySet()) {
			if (nbt.hasKey(property.getName())) {
				properties.put(property, property.readFromNBT(nbt.getTag(property.getName()), this));
			}
		}
	}

	@Override
	public JsonObject writeToJson() {
		JsonObject jsonObject = new JsonObject();
		for (Entry<IProperty, Object> object : properties.entrySet()) {
			if (object.getValue() != null && object.getKey() instanceof IPropertyJson) {
				jsonObject.add(object.getKey().getName(), ((IPropertyJson) object.getKey()).writeToJson(object.getValue()));
			}
		}
		return jsonObject;
	}

	@Override
	public void readFromJson(JsonObject jsonObject) {
		for (IProperty property : properties.keySet()) {
			if (jsonObject.has(property.getName())) {
				properties.put(property, ((IPropertyJson) property).readFromJson(jsonObject.get(property.getName())));
			}
		}
	}
}
