package nedelosk.modularmachines.api.modules.machines.recipe;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.machines.IModuleMachine;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleMachineRecipe<S extends IModuleSaver> extends IModuleMachine<S> {

	boolean addOutput(IModular modular, ModuleStack stack);

	boolean removeInput(IModular modular, ModuleStack stack);

	Object[] getCraftingModifiers(IModular modular, ModuleStack stack);

	void writeCraftingModifiers(NBTTagCompound nbt, IModular modular, Object[] craftingModifiers);

	Object[] readCraftingModifiers(NBTTagCompound nbt, IModular modular);

	String getRecipeName(ModuleStack stack);

	RecipeItem[] getInputs(IModular modular, ModuleStack stack);

	int getItemInputs(ModuleStack stack);

	int getItemOutputs(ModuleStack stack);
}
