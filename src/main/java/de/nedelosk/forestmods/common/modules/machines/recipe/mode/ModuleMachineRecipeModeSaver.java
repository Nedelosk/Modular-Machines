package de.nedelosk.forestmods.common.modules.machines.recipe.mode;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeMode;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeModeSaver;
import de.nedelosk.forestmods.api.recipes.IMachineMode;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeSaver;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleMachineRecipeModeSaver extends ModuleMachineRecipeSaver implements IModuleMachineRecipeModeSaver {

	public IMachineMode mode;

	public ModuleMachineRecipeModeSaver(IMachineMode defaultMode) {
		mode = defaultMode;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		nbt.setInteger("Mode", getMode().ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
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
