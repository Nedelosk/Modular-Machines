package de.nedelosk.forestmods.api.recipes;

import com.google.gson.JsonObject;

import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeHandler {

	JsonObject writeCraftingModifiers(Object[] objects);

	Object[] parseCraftingModifiers(JsonObject object);

	void writeCraftingModifiers(NBTTagCompound nbt, Object[] craftingModifiers);

	Object[] readCraftingModifiers(NBTTagCompound nbt);

	String getRecipeCategory();

	Class<? extends IRecipe> getRecipeClass();
}
