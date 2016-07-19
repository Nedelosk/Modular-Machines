package de.nedelosk.modularmachines.api.property;

import net.minecraft.nbt.NBTTagIntArray;

public class PropertyIntegerArray extends PropertyBase<int[], NBTTagIntArray, IPropertyProvider> {

	public PropertyIntegerArray(String name){
		super(name, int[].class, null);
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}else if (obj instanceof PropertyIntegerArray && super.equals(obj)){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public NBTTagIntArray writeToNBT(IPropertyProvider state, int[] value) {
		return new NBTTagIntArray(value);
	}

	@Override
	public int[] readFromNBT(NBTTagIntArray nbt, IPropertyProvider state) {
		return nbt.getIntArray();
	}
}