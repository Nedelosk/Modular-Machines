package de.nedelosk.forestmods.common.modules.producers.recipe;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.producers.IRecipeManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleProducerRecipeSaver implements IModuleProducerRecipeSaver {

	public IRecipeManager manager;

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		if (nbt.hasKey("Manager")) {
			manager = new ModuleRecipeManager();
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
	public void setRecipeManager(IRecipeManager manager) {
		this.manager = manager;
	}

	@Override
	public IRecipeManager getRecipeManager() {
		return manager;
	}
}
