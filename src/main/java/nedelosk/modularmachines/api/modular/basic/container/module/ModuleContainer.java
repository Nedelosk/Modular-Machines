package nedelosk.modularmachines.api.modular.basic.container.module;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleContainer<P extends IModule> implements ISingleModuleContainer<P> {

	private ModuleStack<P> moduleStack;

	public ModuleContainer(ModuleStack<P> stack) {
		this.moduleStack = stack;
	}

	public ModuleContainer() {
	}

	@Override
	public ModuleStack<P> getStack() {
		return moduleStack;
	}

	@Override
	public void setStack(ModuleStack<P> moduleStack) {
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
