package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.nbt.NBTTagInt;

public class PropertyInteger extends PropertyBase<Integer, NBTTagInt>{

	public PropertyInteger(String name){
		super(name, Integer.class);
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
	public NBTTagInt writeToNBT(IModuleState state, Integer value) {
		return new NBTTagInt(value);
	}

	@Override
	public Integer readFromNBT(NBTTagInt nbt, IModuleState state) {
		return nbt.getInt();
	}
}