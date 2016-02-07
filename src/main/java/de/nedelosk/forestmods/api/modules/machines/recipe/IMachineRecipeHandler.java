package de.nedelosk.forestmods.api.modules.machines.recipe;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import net.minecraft.nbt.NBTTagCompound;

public interface IMachineRecipeHandler {

	void writeToNBT(NBTTagCompound nbt, IModular modular);

	IMachineRecipeHandler readFromNBT(NBTTagCompound nbt, IModular modular);

	int getSpeedModifier();

	RecipeItem[] getOutputs();

	RecipeItem[] getInputs();

	int getMaterialToRemove();
}
