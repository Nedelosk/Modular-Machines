package de.nedelosk.modularmachines.api.recipes;

import java.util.List;

import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyBuilder;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import de.nedelosk.modularmachines.api.property.IPropertySetter;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeHandler{
	
	void serializeNBT(NBTTagCompound nbt, Object[] craftingModifiers);

	Object[] deserializeNBT(NBTTagCompound nbt);
	
	JsonObject serializeJson(Object[] objects);

	Object[] deserializeJson(JsonObject object);

	boolean matches(IRecipe recipe, Object[] craftingModifiers);

	String getRecipeCategory();
}
