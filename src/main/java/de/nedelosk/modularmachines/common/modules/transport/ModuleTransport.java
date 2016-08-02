package de.nedelosk.modularmachines.common.modules.transport;

import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
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

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.INTERNAL;
	}

	@Override
	public EnumModuleSize getSize() {
		return EnumModuleSize.LARGE;
	}

}
