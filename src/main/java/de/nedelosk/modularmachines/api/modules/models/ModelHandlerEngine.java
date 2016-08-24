package de.nedelosk.modularmachines.api.modules.models;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHandlerEngine extends ModelHandlerDefault implements IModelHandler, IModelInitHandler {

	public ModelHandlerEngine(IModuleContainer container) {
		super("engines", container, null);

		this.location = getModelLocation(container.getModule().getSize(container));
	}
}
