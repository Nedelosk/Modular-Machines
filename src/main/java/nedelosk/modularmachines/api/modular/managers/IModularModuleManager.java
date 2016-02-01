package nedelosk.modularmachines.api.modular.managers;

import java.util.HashMap;
import java.util.List;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.IMultiModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.utils.ModuleStack;

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
