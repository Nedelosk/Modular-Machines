package de.nedelosk.modularmachines.api.property;

import de.nedelosk.modularmachines.api.recipes.IToolMode;
import net.minecraft.nbt.NBTTagInt;

public class PropertyMachineMode extends PropertyBase<IToolMode, NBTTagInt, IPropertyProvider> {

	public PropertyMachineMode(String name, Class<? extends IToolMode> valueClass, IToolMode defaultValue) {
		super(name, valueClass, defaultValue);
	}

	@Override
	public NBTTagInt writeToNBT(IPropertyProvider state, IToolMode value) {
		return new NBTTagInt(value.ordinal());
	}

	@Override
	public IToolMode readFromNBT(NBTTagInt nbt, IPropertyProvider state) {
		return getValueClass().getEnumConstants()[nbt.getInt()];
	}

}
