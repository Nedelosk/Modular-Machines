package de.nedelosk.forestmods.common.transport.node;

import de.nedelosk.forestmods.api.transport.node.INodeType;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;

public final class NodeType implements INodeType {

	public final boolean canHandelItems;
	public final boolean canHandelFluids;
	public final boolean canHandelEnergy;

	public NodeType(boolean canHandelItems, boolean canHandelFluids, boolean canHandelEnergy) {
		this.canHandelItems = canHandelItems;
		this.canHandelFluids = canHandelFluids;
		this.canHandelEnergy = canHandelEnergy;
	}

	@Override
	public boolean canHandelItems() {
		return canHandelItems;
	}

	@Override
	public boolean canHandelFluids() {
		return canHandelFluids;
	}

	@Override
	public boolean canHandelEnergy() {
		return canHandelEnergy;
	}

	@Override
	public boolean handleInput(ITransportNode node, Object input) {
		return false;
	}

	@Override
	public boolean handleoutput(ITransportNode node, Object output) {
		return false;
	}
}
