package de.nedelosk.forestmods.library.modules;

import java.util.List;

import de.nedelosk.forestmods.library.modular.ModularException;

public interface IModuleController extends IModule {

	/**
	 * Test has the modular all the required modules.
	 */
	void canAssembleModular() throws ModularException;

	/**
	 * @return The allowed modules for that type of modular controller
	 */
	List<Class<? extends IModule>> getAllowedModules();

	/**
	 * @return The required modules for that type of modular controller
	 */
	List<Class<? extends IModule>> getRequiredModules();
}