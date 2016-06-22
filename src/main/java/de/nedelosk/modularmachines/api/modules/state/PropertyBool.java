package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.nbt.NBTTagByte;

public class PropertyBool extends PropertyBase<Boolean, NBTTagByte>{

    public PropertyBool(String name) {
        super(name, Boolean.class);
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
	public NBTTagByte writeToNBT(IModuleState state, Boolean value) {
		return new NBTTagByte((byte)(value ? 1 : 0));
	}

	@Override
	public Boolean readFromNBT(NBTTagByte nbt, IModuleState state) {
		return nbt.getByte() != 0;
	}
}