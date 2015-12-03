package nedelosk.modularmachines.api.producers.machines.recipe;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.machines.IProducerMachine;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IProducerMachineRecipe extends IProducerMachine {

	boolean addOutput(IModular modular, ModuleStack stack);

	boolean removeInput(IModular modular, ModuleStack stack);
	
	Object[] getCraftingModifiers(IModular modular, ModuleStack stack);
	
	void writeCraftingModifiers(NBTTagCompound nbt, IModular modular, Object[] craftingModifiers);
	
	Object[] readCraftingModifiers(NBTTagCompound nbt, IModular modular);

	String getRecipeName(ModuleStack stack);

	RecipeInput[] getInputs(IModular modular, ModuleStack stack);

}
