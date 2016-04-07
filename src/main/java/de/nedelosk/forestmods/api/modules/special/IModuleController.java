package de.nedelosk.forestmods.api.modules.special;

import java.util.List;

import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModularException;

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