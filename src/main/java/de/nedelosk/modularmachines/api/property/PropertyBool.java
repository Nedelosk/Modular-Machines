package de.nedelosk.modularmachines.api.property;

import com.google.gson.JsonPrimitive;

import net.minecraft.nbt.NBTTagByte;

public class PropertyBool extends PropertyBase<Boolean, NBTTagByte, IPropertyProvider> implements IPropertyJson<Boolean, NBTTagByte, IPropertyProvider, JsonPrimitive> {

	public PropertyBool(String name, boolean defaultValue) {
		super(name, Boolean.class, Boolean.valueOf(defaultValue));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof PropertyBool && super.equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public JsonPrimitive writeToJson(Boolean objects) {
		return new JsonPrimitive(objects);
	}

	@Override
	public Boolean readFromJson(JsonPrimitive object) {
		return object.getAsBoolean();
	}

	@Override
	public NBTTagByte writeToNBT(IPropertyProvider state, Boolean value) {
		return new NBTTagByte((byte) (value ? 1 : 0));
	}

	@Override
	public Boolean readFromNBT(NBTTagByte nbt, IPropertyProvider state) {
		return nbt.getByte() != 0;
	}
}