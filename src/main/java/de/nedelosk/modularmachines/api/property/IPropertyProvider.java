package de.nedelosk.modularmachines.api.property;

import java.util.Map;

import net.minecraft.nbt.NBTBase;

public interface IPropertyProvider {

	<V> V get(IProperty<V, ? extends NBTBase, ? extends IPropertyProvider> property);

	<T, V extends T> IPropertyProvider add(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value);

	IPropertyProvider register(IProperty property);

	Map<IProperty, Object> getProperties();

	IPropertyProvider createProvider();

}
