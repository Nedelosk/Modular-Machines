package de.nedelosk.modularmachines.api.property;

import net.minecraft.nbt.NBTTagByte;

public class PropertyBool extends PropertyBase<Boolean, NBTTagByte, IPropertyProvider>{

	public PropertyBool(String name, boolean defaultValue) {
		super(name, Boolean.class, Boolean.valueOf(defaultValue));
	}

	@Override
	public boolean equals(Object p_equals_1_){
		if (this == p_equals_1_){
			return true;
		}else if (p_equals_1_ instanceof PropertyBool && super.equals(p_equals_1_)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public NBTTagByte writeToNBT(IPropertyProvider state, Boolean value) {
		return new NBTTagByte((byte)(value ? 1 : 0));
	}

	@Override
	public Boolean readFromNBT(NBTTagByte nbt, IPropertyProvider state) {
		return nbt.getByte() != 0;
	}
}