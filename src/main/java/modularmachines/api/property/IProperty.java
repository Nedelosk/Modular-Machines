package modularmachines.api.property;

import net.minecraft.nbt.NBTBase;

public interface IProperty<V, N extends NBTBase, P extends IPropertyProvider> {

	String getName();

	Class<? extends V> getValueClass();

	V getDefaultValue();

	N writeToNBT(P state, V value);

	V readFromNBT(N nbt, P state);
}