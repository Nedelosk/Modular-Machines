package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.IStringSerializable;

public class PropertyEnum<T extends Enum<T> & IStringSerializable> extends PropertyBase<T, NBTTagInt>{

	protected PropertyEnum(String name, Class<T> valueClass, T defaultValue){
		super(name, valueClass, defaultValue);
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}else if (obj instanceof PropertyEnum && super.equals(obj)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public NBTTagInt writeToNBT(IModuleState state, T value) {
		return new NBTTagInt(value.ordinal());
	}

	@Override
	public T readFromNBT(NBTTagInt nbt, IModuleState state) {
		return getValueClass().getEnumConstants()[nbt.getInt()];
	}
}