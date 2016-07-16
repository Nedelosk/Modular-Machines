package de.nedelosk.modularmachines.common.modules.transport;

import de.nedelosk.modularmachines.api.modules.transports.EnumTransportMode;
import de.nedelosk.modularmachines.api.modules.transports.IModuleTransport;
import de.nedelosk.modularmachines.common.modules.Module;

public class ModuleTransport extends Module implements IModuleTransport {

	protected int transportedAmount;

	public ModuleTransport(String name, int complexity, int transportedAmount) {
		super(name, complexity);
		this.transportedAmount = transportedAmount;
	}

	@Override
	public EnumTransportMode getMode() {
		return null;
	}

}
