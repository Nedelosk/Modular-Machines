package de.nedelosk.forestmods.common.modules.producers.recipe.mode;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.recipes.IMachineMode;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.producers.recipe.ModuleProducerRecipeSaver;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleMachineRecipeModeSaver extends ModuleProducerRecipeSaver implements IModuleProducerRecipeModeSaver {

	public IMachineMode mode;

	public ModuleMachineRecipeModeSaver(IMachineMode defaultMode) {
		mode = defaultMode;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		nbt.setInteger("Mode", getCurrentMode().ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		setCurrentMode(((IModuleProducerRecipeMode) stack.getModule()).getModeClass().getEnumConstants()[nbt.getInteger("Mode")]);
	}

	@Override
	public IMachineMode getCurrentMode() {
		return mode;
	}

	@Override
	public void setCurrentMode(IMachineMode mode) {
		this.mode = mode;
	}
}
