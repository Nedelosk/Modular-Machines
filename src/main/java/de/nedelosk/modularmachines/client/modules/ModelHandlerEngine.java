package de.nedelosk.modularmachines.client.modules;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;

public class ModelHandlerEngine extends ModelHandlerDefault implements IModelHandler, IModelInitHandler {

	public ModelHandlerEngine(IModuleContainer container) {
		super("engines", container, null);

		this.location = getModelLocation(container.getModule().getSize(container));
	}
}
