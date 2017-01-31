package modularmachines.common.recipes;

import java.util.List;

import modularmachines.api.recipes.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeStorage {

	List<IRecipe> getRecipes();

	NBTTagCompound writeToNBT(NBTTagCompound nbtTag);

	void readFromNBT(NBTTagCompound nbtTag);
}
