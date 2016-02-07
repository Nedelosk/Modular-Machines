package de.nedelosk.forestmods.common.modules.machines.recipe;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.machines.recipe.IMachineRecipeHandler;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleMachineRecipeSaver implements IModuleMachineRecipeSaver {

	public IMachineRecipeHandler manager;

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		if (nbt.hasKey("Manager")) {
			manager = new MachineRecipeHandler();
			manager = manager.readFromNBT(nbt.getCompoundTag("Manager"), modular);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		if (manager != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			manager.writeToNBT(nbtTag, modular);
			nbt.setTag("Manager", nbtTag);
		}
	}

	@Override
	public void setRecipeManager(IMachineRecipeHandler manager) {
		this.manager = manager;
	}

	@Override
	public IMachineRecipeHandler getRecipeManager() {
		return manager;
	}
}
