package nedelosk.modularmachines.api.producers.managers.fluids;

import nedelosk.modularmachines.api.producers.fluids.ITankManager;
import nedelosk.modularmachines.api.producers.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.producers.managers.IProducerManager;

public interface IProducerTankManager extends IProducerGuiWithWidgets, IProducerManager {

	ITankManager getManager();

}
