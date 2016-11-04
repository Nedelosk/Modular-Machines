package modularmachines.api.recipes;

import modularmachines.api.property.IProperty;
import modularmachines.api.property.IPropertyBuilder;
import modularmachines.api.property.IPropertyProvider;
import modularmachines.api.property.IPropertySetter;
import net.minecraft.nbt.NBTBase;

public interface IRecipeBuilder extends IPropertyBuilder, IPropertySetter<IRecipeBuilder> {

	@Override
	<T, V extends T> IRecipeBuilder set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value);

	@Override
	IRecipe build();
}
