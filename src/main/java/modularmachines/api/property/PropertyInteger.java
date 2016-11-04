package modularmachines.api.property;

import com.google.gson.JsonPrimitive;

import net.minecraft.nbt.NBTTagInt;

public class PropertyInteger extends PropertyBase<Integer, NBTTagInt, IPropertyProvider> implements IPropertyJson<Integer, NBTTagInt, IPropertyProvider, JsonPrimitive> {

	public PropertyInteger(String name, int defaultValue) {
		super(name, Integer.class, Integer.valueOf(defaultValue));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof PropertyInteger && super.equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public JsonPrimitive writeToJson(Integer objects) {
		return new JsonPrimitive(objects.intValue());
	}

	@Override
	public Integer readFromJson(JsonPrimitive object) {
		return Integer.valueOf(object.getAsInt());
	}

	@Override
	public NBTTagInt writeToNBT(IPropertyProvider state, Integer value) {
		return new NBTTagInt(value);
	}

	@Override
	public Integer readFromNBT(NBTTagInt nbt, IPropertyProvider state) {
		return nbt.getInt();
	}
}