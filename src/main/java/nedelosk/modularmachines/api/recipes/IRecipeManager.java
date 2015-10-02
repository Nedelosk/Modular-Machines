package nedelosk.modularmachines.api.recipes;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeManager {

	void writeToNBT(NBTTagCompound nbt);
	
	IRecipeManager readFromNBT(NBTTagCompound nbt, IModular modular);
	
	boolean removeEnergy();
	
	int getSpeedModifier();
	
	RecipeItem[] getOutputs();
	
	RecipeInput[] getInputs();
	
}
