package de.nedelosk.forestmods.api.transport.node;

import java.util.List;

public interface INodeType {

	List<Class<? extends IContentHandler>> getHandlers(INodeSide side);

	int updatesPerTick();
}
