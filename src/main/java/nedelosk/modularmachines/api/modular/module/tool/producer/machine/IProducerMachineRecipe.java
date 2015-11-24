package nedelosk.modularmachines.api.modular.module.tool.producer.machine;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
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
