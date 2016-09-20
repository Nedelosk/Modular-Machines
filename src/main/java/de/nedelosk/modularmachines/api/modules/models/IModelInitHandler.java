package de.nedelosk.modularmachines.api.modules.models;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;

public interface IModelInitHandler {

	/**
	 * To register the textures of the models and other stuff.
	 */
	void initModels(IModuleContainer container);
}
