package de.nedelosk.modularmachines.api.recipes;

import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyBuilder;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.property.IPropertySetter;
import net.minecraft.nbt.NBTBase;

public interface IRecipeBuilder extends IPropertyBuilder, IPropertySetter<IRecipeBuilder>{

	@Override
	<T, V extends T> IRecipeBuilder set(IProperty<T, ? extends NBTBase, ? extends IPropertyProvider> property, V value);

	@Override
	IRecipe build();

}
