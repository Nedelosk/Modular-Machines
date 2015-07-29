package nedelosk.modularmachines.api.modular.module.recipes;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.common.modular.ModularMachine;
import nedelosk.modularmachines.common.modular.module.tool.producer.RecipeManager;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public interface IRecipeManager {

	void writeToNBT(NBTTagCompound nbt);
	
	IRecipeManager readFromNBT(NBTTagCompound nbt, IModular modular);
	
	boolean removeEnergy();
	
	int getSpeedModifier();
	
	RecipeItem[] getOutputs();
	
	RecipeInput[] getInputs();
	
}
