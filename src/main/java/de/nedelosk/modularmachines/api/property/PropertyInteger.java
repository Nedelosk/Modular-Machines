package de.nedelosk.modularmachines.api.property;

import net.minecraft.nbt.NBTTagInt;

public class PropertyInteger extends PropertyBase<Integer, NBTTagInt, IPropertyProvider>{

	public PropertyInteger(String name, int defaultValue){
		super(name, Integer.class, Integer.valueOf(defaultValue));
	}

	@Override
	public boolean equals(Object p_equals_1_){
		if (this == p_equals_1_){
			return true;
		}else if (p_equals_1_ instanceof PropertyInteger && super.equals(p_equals_1_)){
			return true;
		}else{
			return false;
		}
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