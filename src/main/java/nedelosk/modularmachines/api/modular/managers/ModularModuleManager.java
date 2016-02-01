package nedelosk.modularmachines.api.modular.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.container.gui.IMultiGuiContainer;
import nedelosk.modularmachines.api.modules.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.IMultiModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.ModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.MultiModuleContainer;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ModularModuleManager<M extends IModular> implements IModularModuleManager<M> {

	public M modular;
	protected HashMap<String, IModuleContainer> modules = Maps.newHashMap();

	public ModularModuleManager() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList listTag = nbt.getTagList("Modules", 10);
		for ( int i = 0; i < listTag.tagCount(); i++ ) {
			NBTTagCompound modulesTag = listTag.getCompoundTagAt(i);
			IModuleContainer container;
			IModule module;
			if (modulesTag.getBoolean("IsMulti")) {
				container = new MultiModuleContainer();
			} else {
				container = new ModuleContainer();
			}
			container.readFromNBT(modulesTag, modular);
			if (modulesTag.getBoolean("IsMulti")) {
				if (((IMultiModuleContainer) container).getStacks().isEmpty() || ((IMultiModuleContainer) container).getStack(0) == null) {
					continue;
				}
				module = ((IMultiModuleContainer) container).getStack(0).getModule();
			} else {
				if (((ISingleModuleContainer) container).getStack() == null) {
					continue;
				}
				module = ((ISingleModuleContainer) container).getStack().getModule();
			}
			if (module == null) {
				continue;
			}
			modules.put(module.getCategoryUID(), container);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList listTag = new NBTTagList();
		for ( Entry<String, IModuleContainer> module : modules.entrySet() ) {
			NBTTagCompound modulesTag = new NBTTagCompound();
			IModuleContainer container = module.getValue();
			container.writeToNBT(modulesTag, modular);
			modulesTag.setBoolean("IsMulti", container instanceof IMultiModuleContainer);
			listTag.appendTag(modulesTag);
		}
		nbt.setTag("Modules", listTag);
	}

	@Override
	public M getModular() {
		return modular;
	}

	@Override
	public void setModular(M modular) {
		this.modular = modular;
	}

	@Override
	public HashMap<String, IModuleContainer> getModuleContainers() {
		return modules;
	}

	@Override
	public boolean addModule(ModuleStack stack) {
		if (stack == null) {
			return false;
		}
		IModule module = stack.getModule();
		if (module == null) {
			return false;
		}
		if (modules.get(module.getCategoryUID()) == null) {
			Class<? extends IModuleContainer> containerClass = ModuleRegistry.getCategory(module.getCategoryUID()).getModuleContainerClass();
			IModuleContainer container;
			try {
				if (containerClass.isInterface()) {
					return false;
				}
				container = containerClass.newInstance();
			} catch (Exception e) {
				return false;
			}
			if (container instanceof ISingleModuleContainer) {
				((ISingleModuleContainer) container).setStack(stack);
			} else if (container instanceof IMultiModuleContainer) {
				((IMultiModuleContainer) container).addStack(stack);
			}
			container.setCategoryUID(stack.getModule().getCategoryUID());
			modules.put(module.getCategoryUID(), container);
		} else {
			IModuleContainer container = modules.get(module.getCategoryUID());
			if (container instanceof IMultiGuiContainer) {
				((IMultiModuleContainer) container).addStack(stack);
			}
		}
		return true;
	}

	@Override
	public ISingleModuleContainer getSingleModule(String moduleName) {
		IModuleContainer conatiner = getModule(moduleName);
		if (conatiner == null) {
			return null;
		}
		if (!(conatiner instanceof ISingleModuleContainer)) {
			return null;
		}
		return (ISingleModuleContainer) conatiner;
	}

	@Override
	public IMultiModuleContainer getMultiModule(String moduleName) {
		IModuleContainer conatiner = getModule(moduleName);
		if (conatiner == null) {
			return null;
		}
		if (!(conatiner instanceof IMultiModuleContainer)) {
			return null;
		}
		return (IMultiModuleContainer) conatiner;
	}

	@Override
	public IModuleContainer getModule(String moduleName) {
		return modules.get(moduleName);
	}

	@Override
	public ModuleStack getModuleFromUID(String UID) {
		if (UID == null) {
			return null;
		}
		String[] uids = UID.split(":");
		if (uids == null || uids.length < 1 || 2 < uids.length) {
			return null;
		}
		IModuleContainer container = getModule(uids[0]);
		if (container != null) {
			if (container instanceof ISingleModuleContainer) {
				return ((ISingleModuleContainer) container).getStack();
			} else if (container instanceof IMultiModuleContainer) {
				return ((IMultiModuleContainer) container).getStack(uids[1]);
			}
		}
		return null;
	}

	@Override
	public List<ModuleStack> getModuleStacks() {
		List<ModuleStack> stacks = new ArrayList();
		for ( IModuleContainer module : modules.values() ) {
			if (module instanceof ISingleModuleContainer) {
				stacks.add(((ISingleModuleContainer) module).getStack());
			} else if (module instanceof IMultiModuleContainer) {
				stacks.addAll(((IMultiModuleContainer) module).getStacks());
			}
		}
		return stacks;
	}
}
