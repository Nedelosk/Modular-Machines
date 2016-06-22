package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeManager {

	void writeToNBT(NBTTagCompound nbt, IModuleState state);

	IRecipeManager readFromNBT(NBTTagCompound nbt, IModuleState state);

	int getSpeedModifier();

	RecipeItem[] getOutputs();

	RecipeItem[] getInputs();

	int getMaterialToRemove();
}
