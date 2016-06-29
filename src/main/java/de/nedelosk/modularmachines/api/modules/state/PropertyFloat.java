package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.nbt.NBTTagFloat;

public class PropertyFloat extends PropertyBase<Float, NBTTagFloat>{

	public PropertyFloat(String name, float defaultValue){
		super(name, Float.class, Float.valueOf(defaultValue));
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}else if (obj instanceof PropertyFloat && super.equals(obj)){
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