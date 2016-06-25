package de.nedelosk.modularmachines.api.modules.state;

import net.minecraft.nbt.NBTBase;

public interface IProperty<V, N extends NBTBase>{
	String getName();

	Class<? extends V> getValueClass();

	N writeToNBT(IModuleState state, V value);

	V readFromNBT(N nbt, IModuleState state);
}