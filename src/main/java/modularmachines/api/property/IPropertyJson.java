package modularmachines.api.property;

import com.google.gson.JsonElement;

import net.minecraft.nbt.NBTBase;

public interface IPropertyJson<V, N extends NBTBase, P extends IPropertyProvider, J extends JsonElement> extends IProperty<V, N, P> {

	J writeToJson(V objects);

	V readFromJson(J object);
}
