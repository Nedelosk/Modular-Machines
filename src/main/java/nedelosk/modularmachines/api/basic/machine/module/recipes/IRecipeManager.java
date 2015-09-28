package nedelosk.modularmachines.api.basic.machine.module.recipes;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeManager {

	void writeToNBT(NBTTagCompound nbt);
	
	IRecipeManager readFromNBT(NBTTagCompound nbt, IModular modular);
	
	boolean removeEnergy();
	
	int getSpeedModifier();
	
	RecipeItem[] getOutputs();
	
	RecipeInput[] getInputs();
	
}
