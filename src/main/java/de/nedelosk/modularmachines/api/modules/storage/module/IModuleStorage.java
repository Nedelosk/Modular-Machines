package de.nedelosk.modularmachines.api.modules.storage.module;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

/**
 * A storage to storage module states. It stores his own parent and other modules states.
 */
public interface IModuleStorage {

	/**
	 * @return All module states that are have a module that class is a instance of of moduleClass.
	 */
	<M extends IModule> List<IModuleState<M>> getModules(Class<? extends M> moduleClass);

	/**
	 * @return All modules states in a list.
	 */
	List<IModuleState> getModules();

	List<IModuleProvider> getProviders();

	/**
	 * @return A module state that have the index.
	 */
	<M extends IModule> IModuleState<M> getModule(int index);

	/**
	 * @return The first module state that have a module that class is a instance of of moduleClass.
	 */
	<M extends IModule> IModuleState<M> getModule(Class<? extends M> moduleClass);

}
