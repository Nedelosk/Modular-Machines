package de.nedelosk.modularmachines.api.property;

import java.util.Map;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IPropertyProvider {

	<V> V get(IProperty<V, ? extends NBTBase, ? extends IPropertyProvider> property);

	Map<IProperty, Object> getProperties();

	void writeToNBT(NBTTagCompound nbt);

	void readFromNBT(NBTTagCompound nbt);

}
