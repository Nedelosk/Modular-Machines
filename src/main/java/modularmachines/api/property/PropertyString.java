package modularmachines.api.property;

import com.google.gson.JsonPrimitive;

import net.minecraft.nbt.NBTTagString;

public class PropertyString extends PropertyBase<String, NBTTagString, IPropertyProvider> implements IPropertyJson<String, NBTTagString, IPropertyProvider, JsonPrimitive> {

	public PropertyString(String name, String defaultValue) {
		super(name, String.class, defaultValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof PropertyString && super.equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public JsonPrimitive writeToJson(String objects) {
		return new JsonPrimitive(objects);
	}

	@Override
	public String readFromJson(JsonPrimitive object) {
		return object.getAsString();
	}

	@Override
	public NBTTagString writeToNBT(IPropertyProvider state, String value) {
		return new NBTTagString(value);
	}

	@Override
	public String readFromNBT(NBTTagString nbt, IPropertyProvider state) {
		return nbt.getString();
	}
}