package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.nbt.NBTTagFloat;

public class PropertyFloat extends PropertyBase<Float, NBTTagFloat>{

	public PropertyFloat(String name){
		super(name, Float.class);
	}

	@Override
	public boolean equals(Object p_equals_1_){
		if (this == p_equals_1_){
			return true;
		}else if (p_equals_1_ instanceof PropertyFloat && super.equals(p_equals_1_)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public NBTTagFloat writeToNBT(IModuleState state, Float value) {
		return new NBTTagFloat(value);
	}

	@Override
	public Float readFromNBT(NBTTagFloat nbt, IModuleState state) {
		return nbt.getFloat();
	}
}