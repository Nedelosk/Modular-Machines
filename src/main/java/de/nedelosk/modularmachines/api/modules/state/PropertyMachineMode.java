package de.nedelosk.modularmachines.api.modules.state;

import de.nedelosk.modularmachines.api.recipes.IMachineMode;
import net.minecraft.nbt.NBTTagInt;

public class PropertyMachineMode extends PropertyBase<IMachineMode, NBTTagInt> {

	public PropertyMachineMode(String name, Class<? extends IMachineMode> valueClass, IMachineMode defaultValue) {
		super(name, valueClass, defaultValue);
	}

	@Override
	public NBTTagInt writeToNBT(IModuleState state, IMachineMode value) {
		return new NBTTagInt(value.ordinal());
	}

	@Override
	public IMachineMode readFromNBT(NBTTagInt nbt, IModuleState state) {
		return getValueClass().getEnumConstants()[nbt.getInt()];
	}

}
