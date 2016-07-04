package de.nedelosk.modularmachines.api.property;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IPropertySetter<O> {
	
	<T, V extends T> O set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value);
}
