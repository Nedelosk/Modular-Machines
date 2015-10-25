package nedelosk.modularmachines.api.recipes;

import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeManager {

	void writeToNBT(NBTTagCompound nbt) throws Exception;
	
	boolean removeEnergy();
	
	int getSpeedModifier();
	
	RecipeItem[] getOutputs();
	
	RecipeInput[] getInputs();
	
}
