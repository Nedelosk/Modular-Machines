package de.nedelosk.modularmachines.common.modules.transport;

import de.nedelosk.modularmachines.api.modules.IModuleTransport;
import de.nedelosk.modularmachines.common.modules.Module;

public class ModuleTransport extends Module implements IModuleTransport {

	protected int transportedAmount;

	public ModuleTransport(int transportedAmount) {
		this.transportedAmount = transportedAmount;
	}

}
