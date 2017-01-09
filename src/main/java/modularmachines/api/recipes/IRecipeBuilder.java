package modularmachines.api.recipes;

import net.minecraft.nbt.NBTBase;

import modularmachines.api.property.IProperty;
import modularmachines.api.property.IPropertyBuilder;
import modularmachines.api.property.IPropertyProvider;
import modularmachines.api.property.IPropertySetter;

public interface IRecipeBuilder extends IPropertyBuilder, IPropertySetter<IRecipeBuilder> {

	@Override
	<T, V extends T> IRecipeBuilder setValue(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value);

	@Override
	IRecipe init();
}
