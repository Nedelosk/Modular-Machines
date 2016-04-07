package de.nedelosk.forestmods.api.modules;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeManager {

	void writeToNBT(NBTTagCompound nbt, IModular modular);

	IRecipeManager readFromNBT(NBTTagCompound nbt, IModular modular);

	int getSpeedModifier();

	RecipeItem[] getOutputs();

	RecipeItem[] getInputs();

	int getMaterialToRemove();
}
