package nedelosk.modularmachines.api.modular.basic;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleContainer<M extends IModule, P extends IProducer> implements IModuleContainer<ModuleStack<M, P>> {
	
	private ModuleStack<M, P> moduleStack;
	
	
	public ModuleContainer(ModuleStack<M, P> moduleStack) {
		this.moduleStack = moduleStack;
	}
	
	public ModuleStack<M, P> getModuleStack() {
		return moduleStack;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		if(moduleStack != null)
			moduleStack.writeToNBT(nbt, modular);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		moduleStack = new ModuleStack<M, P>(nbt, modular);
	}
	
}
