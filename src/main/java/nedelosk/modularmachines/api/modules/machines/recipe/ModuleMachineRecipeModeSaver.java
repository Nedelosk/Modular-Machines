package nedelosk.modularmachines.api.modules.machines.recipe;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.machines.ModuleMachineSaver;
import nedelosk.modularmachines.api.recipes.IMachineMode;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleMachineRecipeModeSaver extends ModuleMachineSaver implements IModuleMachineRecipeModeSaver {

	public IMachineMode mode;

	public ModuleMachineRecipeModeSaver(IMachineMode defaultMode) {
		mode = defaultMode;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("Mode", getMode().ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		setMode(((IModuleMachineRecipeMode) stack.getModule()).getModeClass().getEnumConstants()[nbt.getInteger("Mode")]);
	}

	@Override
	public IMachineMode getMode() {
		return mode;
	}

	@Override
	public void setMode(IMachineMode mode) {
		this.mode = mode;
	}
}
