package modularmachines.api.property;

import java.util.Map;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPropertyProvider extends INBTSerializable<NBTTagCompound> {

	<V> V get(IProperty<V, ? extends NBTBase, ? extends IPropertyProvider> property);

	Map<IProperty, Object> getProperties();
}
