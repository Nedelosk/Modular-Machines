package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.api.ForestModsApi;
import de.nedelosk.forestmods.common.transport.node.ItemHandler;
import de.nedelosk.forestmods.common.transport.node.NodeType;

public class TransportManager {

	public static void registerTransport() {
		ForestModsApi.addNodeTypes(new NodeType(40, ItemHandler.class));
	}
}
