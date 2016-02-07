package de.nedelosk.forestmods.common.modular.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.modules.basic.IModuleCategory;
import de.nedelosk.forestmods.api.modules.container.IModuleContainer;
import de.nedelosk.forestmods.api.modules.container.IMultiModuleContainer;
import de.nedelosk.forestmods.api.modules.container.ISingleModuleContainer;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
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
			String categoryUID = modulesTag.getString("CategoryUID");
			IModuleCategory category = ModuleRegistry.getCategory(categoryUID);
			if (category == null) {
				continue;
			}
			Class<? extends IModuleContainer> containerClass = category.getModuleContainerClass();
			IModuleContainer container;
			try {
				if (containerClass.isInterface()) {
					continue;
				}
				container = containerClass.newInstance();
			} catch (Exception e) {
				continue;
			}
			container.readFromNBT(modulesTag, modular);
			modules.put(categoryUID, container);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList listTag = new NBTTagList();
		for ( Entry<String, IModuleContainer> module : modules.entrySet() ) {
			NBTTagCompound modulesTag = new NBTTagCompound();
			IModuleContainer container = module.getValue();
			container.writeToNBT(modulesTag, modular);
			modulesTag.setString("CategoryUID", container.getCategoryUID());
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
		stack = stack.copy();
		IModule module = stack.getModule();
		if (module == null) {
			return false;
		}
		if (modules.get(module.getCategoryUID()) == null) {
			if (ModuleRegistry.getCategory(module.getCategoryUID()) == null) {
				return false;
			}
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
			if (stack.getModule() instanceof IModuleAddable) {
				try {
					((IModuleAddable) stack.getModule()).onAddInModular(modular, stack);
				} catch (Exception e) {
					return false;
				}
			}
			if (container instanceof ISingleModuleContainer) {
				((ISingleModuleContainer) container).setStack(stack);
			} else if (container instanceof IMultiModuleContainer) {
				((IMultiModuleContainer) container).addStack(stack);
			}
			container.setCategoryUID(stack.getModule().getCategoryUID());
			modules.put(module.getCategoryUID(), container);
			return true;
		} else {
			IModuleContainer container = modules.get(module.getCategoryUID());
			if (container instanceof IMultiModuleContainer) {
				if (((IMultiModuleContainer) container).getStack(stack.getModule().getModuleUID()) == null) {
					if (stack.getModule() instanceof IModuleAddable) {
						try {
							((IModuleAddable) stack.getModule()).onAddInModular(modular, stack);
						} catch (Exception e) {
							return false;
						}
					}
					((IMultiModuleContainer) container).addStack(stack);
					return true;
				}
				return false;
			}
		}
		return false;
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
