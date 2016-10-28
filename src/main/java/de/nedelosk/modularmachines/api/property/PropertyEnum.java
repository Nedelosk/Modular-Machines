package de.nedelosk.modularmachines.api.property;

import net.minecraft.nbt.NBTTagInt;

public class PropertyEnum<T extends Enum<T>> extends PropertyBase<T, NBTTagInt, IPropertyProvider> {

	public PropertyEnum(String name, Class<T> valueClass, T defaultValue) {
		super(name, valueClass, defaultValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof PropertyEnum && super.equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public NBTTagInt writeToNBT(IPropertyProvider state, T value) {
		return new NBTTagInt(value.ordinal());
	}

	@Override
	public T readFromNBT(NBTTagInt nbt, IPropertyProvider state) {
		return getValueClass().getEnumConstants()[nbt.getInt()];
	}
}