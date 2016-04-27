package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.common.transport.node.ItemHandler;
import de.nedelosk.forestmods.common.transport.node.NodeType;
import de.nedelosk.forestmods.library.ForestModsApi;

public class TransportManager {

	public static void registerTransport() {
		ForestModsApi.addNodeTypes(new NodeType(40, ItemHandler.class));
	}
}
