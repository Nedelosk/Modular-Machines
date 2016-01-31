package nedelosk.modularmachines.api.modular.basic.container.module;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleContainer<M extends IModule<S>, S extends IModuleSaver> implements ISingleModuleContainer<M, S> {

	private ModuleStack<M, S> moduleStack;

	public ModuleContainer(ModuleStack<M, S> stack) {
		this.moduleStack = stack;
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

	/* NBT */
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		if (moduleStack != null) {
			moduleStack.writeToNBT(nbt, modular);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		moduleStack = ModuleStack.loadFromNBT(nbt, modular);
	}
}
