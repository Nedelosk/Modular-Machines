package de.nedelosk.modularmachines.api.recipes;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeStorage {

	List<IRecipe> getRecipes();

	NBTTagCompound writeToNBT(NBTTagCompound nbtTag);

	void readFromNBT(NBTTagCompound nbtTag);

}
