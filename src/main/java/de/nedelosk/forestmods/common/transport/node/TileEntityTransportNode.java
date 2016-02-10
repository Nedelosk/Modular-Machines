package de.nedelosk.forestmods.common.transport.node;

import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import de.nedelosk.forestmods.common.transport.TileEntityTransport;

public class TileEntityTransportNode extends TileEntityTransport {

	protected ITransportNode node;

	public TileEntityTransportNode() {
		node = new TransportNode(this);
	}

	@Override
	public ITransportNode getTransportPart() {
		return node;
	}

	@Override
	public void updateEntity() {
	}
}
