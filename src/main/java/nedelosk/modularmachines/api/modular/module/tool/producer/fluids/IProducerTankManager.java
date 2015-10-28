package nedelosk.modularmachines.api.modular.module.tool.producer.fluids;

import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGuiWithWidgets;

public interface IProducerTankManager extends IProducerGuiWithWidgets, IProducerManager {

	ITankManager getManager();

}
