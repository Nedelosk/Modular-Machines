package nedelosk.modularmachines.api.utils;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public final class ModuleStack<M extends IModule> {

	private M module;
	private IModuleSaver saver;

	public ModuleStack(M module) {
		this.module = module;
		this.saver = module.getSaver(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModuleStack) {
			ModuleStack producerStack = (ModuleStack) obj;
			if (module == null && producerStack.getModule() == null || module.getName(this).equals(producerStack.getModule().getName(producerStack))) {
				return true;
			}
		}
		return false;
	}

	public static ModuleStack loadFromNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		ResourceLocation registry = new ResourceLocation(nbt.getString("Registry"));
		IModule producer = ModuleRegistry.getProducer(registry);
		ModuleStack stack = new ModuleStack(producer);
		if (stack.saver != null) {
			stack.saver.readFromNBT(nbt.getCompoundTag("saver"), modular, stack);
		}
		return stack;
	}

	public void writeToNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		nbt.setString("Registry", module.getRegistry().toString());
		if (saver != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			saver.writeToNBT(nbtTag, modular, this);
			nbt.setTag("saver", nbtTag);
		}
	}

	public M getModule() {
		return module;
	}

	public IModuleSaver getSaver() {
		return saver;
	}

	public ModuleStack copy() {
		return new ModuleStack(module);
	}
}
