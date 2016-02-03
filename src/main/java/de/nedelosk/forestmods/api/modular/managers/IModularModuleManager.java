package de.nedelosk.forestmods.api.modular.managers;

import java.util.HashMap;
import java.util.List;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.container.IModuleContainer;
import de.nedelosk.forestmods.api.modules.container.IMultiModuleContainer;
import de.nedelosk.forestmods.api.modules.container.ISingleModuleContainer;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModularModuleManager<M extends IModular> extends IModularManager<M> {

	boolean addModule(ModuleStack stack);

	IModuleContainer getModule(String categoryUID);

	ModuleStack getModuleFromUID(String UID);

	ISingleModuleContainer getSingleModule(String categoryUID);

	IMultiModuleContainer getMultiModule(String categoryUID);

	/**
	 * @return All modules in a HashMap
	 */
	HashMap<String, IModuleContainer> getModuleContainers();

	/**
	 * @return All modules as ModuleStack
	 */
	List<ModuleStack> getModuleStacks();
}
