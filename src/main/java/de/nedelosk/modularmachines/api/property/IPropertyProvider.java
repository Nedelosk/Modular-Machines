package de.nedelosk.modularmachines.api.property;

import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTBase;

public interface IPropertyProvider {

	<V> V get(IProperty<V, ? extends NBTBase, ? extends IPropertyProvider> property);
	
	Map<IProperty, Object> getProperties();

}
