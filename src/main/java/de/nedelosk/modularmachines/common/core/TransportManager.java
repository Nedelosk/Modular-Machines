package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.common.transport.node.ItemHandler;
import de.nedelosk.modularmachines.common.transport.node.NodeType;

public class TransportManager {

	public static void registerTransport() {
		ModularMachinesApi.addNodeTypes(new NodeType(40, ItemHandler.class));
	}
}
