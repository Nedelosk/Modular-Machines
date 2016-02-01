package nedelosk.modularmachines.api.modules.container.module;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleContainer<M extends IModule, S extends IModuleSaver> implements ISingleModuleContainer<M, S> {

	private ModuleStack<M, S> moduleStack;
	private String categoryUID;

	public ModuleContainer(ModuleStack<M, S> stack, String categoryUID) {
		this.moduleStack = stack;
		this.categoryUID = categoryUID;
	}

	public ModuleContainer() {
	}

	@Override
	public ModuleStack<M, S> getStack() {
		return moduleStack;
	}

	@Override
	public void setStack(ModuleStack<M, S> moduleStack) {
		this.moduleStack = moduleStack;
	}
	
	public void setCategoryUID(String categoryUID) {
		this.categoryUID = categoryUID;
	}
	
	@Override
	public String getCategoryUID() {
		return categoryUID;
	}

	/* NBT */
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		if (moduleStack != null) {
			moduleStack.writeToNBT(nbt, modular);
		}
		nbt.setString("CategoryUID", categoryUID);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		moduleStack = ModuleStack.loadFromNBT(nbt, modular);
		categoryUID = nbt.getString("CategoryUID");
	}
}
