package modularmachines.api.property;

import net.minecraft.nbt.NBTBase;

public interface IPropertySetter<O> {

	<T, V extends T> O setValue(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value);
}
