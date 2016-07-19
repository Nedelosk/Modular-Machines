package de.nedelosk.modularmachines.api.property;

import com.google.gson.JsonPrimitive;

import net.minecraft.nbt.NBTTagDouble;

public class PropertyDouble extends PropertyBase<Double, NBTTagDouble, IPropertyProvider> implements IPropertyJson<Double, NBTTagDouble, IPropertyProvider, JsonPrimitive>{

	public PropertyDouble(String name, double defaultValue){
		super(name, Double.class, Double.valueOf(defaultValue));
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}else if (obj instanceof PropertyDouble && super.equals(obj)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public JsonPrimitive writeToJson(Double objects) {
		return new JsonPrimitive(objects);
	}

	@Override
	public Double readFromJson(JsonPrimitive object) {
		return object.getAsDouble();
	}

	@Override
	public NBTTagDouble writeToNBT(IPropertyProvider state, Double value) {
		return new NBTTagDouble(value);
	}

	@Override
	public Double readFromNBT(NBTTagDouble nbt, IPropertyProvider state) {
		return nbt.getDouble();
	}
}