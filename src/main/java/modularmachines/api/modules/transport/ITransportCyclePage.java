package modularmachines.api.modules.transport;

import modularmachines.api.gui.IPage;
import modularmachines.api.modular.handlers.IModularHandler;

public interface ITransportCyclePage<H, T extends ITransportCycle<H>> extends IPage<IModularHandler> {

	ITransportCycle<H> createCycle();

	int getPlayerInvPosition();
}
