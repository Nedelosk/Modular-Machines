package de.nedelosk.modularmachines.common.modules.transport;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.transports.EnumTransportMode;
import de.nedelosk.modularmachines.api.modules.transports.IModuleTransport;
import de.nedelosk.modularmachines.common.modules.Module;

public class ModuleTransport extends Module implements IModuleTransport {

	protected int transportedAmount;

	public ModuleTransport(String name) {
		super(name);
	}

	@Override
	public EnumTransportMode getMode() {
		return null;
	}

	@Override
	public EnumStoragePosition getPosition(IModuleContainer container) {
		return EnumStoragePosition.INTERNAL;
	}

}
