package nedelosk.modularmachines.api.recipes;

import nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeManager {

	void writeToNBT(NBTTagCompound nbt, IModular modular);

	IRecipeManager readFromNBT(NBTTagCompound nbt, IModular modular);

	boolean removeMaterial();

	int getSpeedModifier();

	RecipeItem[] getOutputs();

	RecipeItem[] getInputs();
}
