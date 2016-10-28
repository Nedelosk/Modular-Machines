package de.nedelosk.modularmachines.api.property;

import com.google.gson.JsonPrimitive;

import de.nedelosk.modularmachines.api.recipes.IToolMode;
import net.minecraft.nbt.NBTTagInt;

public class PropertyToolMode extends PropertyBase<IToolMode, NBTTagInt, IPropertyProvider> implements IPropertyJson<IToolMode, NBTTagInt, IPropertyProvider, JsonPrimitive> {

	public PropertyToolMode(String name, Class<? extends IToolMode> valueClass, IToolMode defaultValue) {
		super(name, valueClass, defaultValue);
	}

	@Override
	public NBTTagInt writeToNBT(IPropertyProvider state, IToolMode value) {
		return new NBTTagInt(value.ordinal());
	}

	@Override
	public IToolMode readFromNBT(NBTTagInt nbt, IPropertyProvider state) {
		return getValueClass().getEnumConstants()[nbt.getInt()];
	}

	@Override
	public JsonPrimitive writeToJson(IToolMode objects) {
		return new JsonPrimitive(objects.ordinal());
	}

	@Override
	public IToolMode readFromJson(JsonPrimitive object) {
		return getValueClass().getEnumConstants()[object.getAsInt()];
	}
}
