package de.nedelosk.forestmods.common.transport.node;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.api.transport.node.IContentHandler;
import de.nedelosk.forestmods.api.transport.node.INodeSide;
import de.nedelosk.forestmods.api.transport.node.INodeType;

public final class NodeType implements INodeType {

	public final int updatesPerTick;
	public final List<Class<? extends IContentHandler>> handlers;

	public NodeType(int updatesPerTick, Class<? extends IContentHandler>... handlers) {
		this.updatesPerTick = updatesPerTick;
		this.handlers = Lists.newArrayList();
		for(Class<? extends IContentHandler> handler : handlers) {
			this.handlers.add(handler);
		}
	}

	@Override
	public int updatesPerTick() {
		return updatesPerTick;
	}

	@Override
	public List<Class<? extends IContentHandler>> getHandlers(INodeSide side) {
		return handlers;
	}
}
