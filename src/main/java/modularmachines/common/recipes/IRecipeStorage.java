package modularmachines.common.recipes;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.recipes.IRecipe;

public interface IRecipeStorage {

	List<IRecipe> getRecipes();

	NBTTagCompound writeToNBT(NBTTagCompound nbtTag);

	void readFromNBT(NBTTagCompound nbtTag);
}
