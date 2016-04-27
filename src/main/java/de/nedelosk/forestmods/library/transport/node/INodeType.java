package de.nedelosk.forestmods.library.transport.node;

import java.util.List;

public interface INodeType {

	List<Class<? extends IContentHandler>> getHandlers(INodeSide side);

	int updatesPerTick();
}
