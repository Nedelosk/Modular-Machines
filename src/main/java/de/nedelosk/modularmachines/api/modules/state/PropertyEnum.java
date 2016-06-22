package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.IStringSerializable;

public class PropertyEnum<T extends Enum<T> & IStringSerializable> extends PropertyBase<T, NBTTagInt>{

    protected PropertyEnum(String name, Class<T> valueClass){
        super(name, valueClass);
    }

    @Override
	public boolean equals(Object p_equals_1_){
        if (this == p_equals_1_){
            return true;
        }else if (p_equals_1_ instanceof PropertyEnum && super.equals(p_equals_1_)){
            return true;
        }else{
            return false;
        }
    }

    public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz) {
        return new PropertyEnum(name, clazz);
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