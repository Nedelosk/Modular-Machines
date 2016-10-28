package de.nedelosk.modularmachines.api.modules.transport;

import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;

public interface ITransportCyclePage<H, T extends ITransportCycle<H>> extends IPage<IModularHandler> {

	ITransportCycle<H> createCycle();

	int getPlayerInvPosition();
}
