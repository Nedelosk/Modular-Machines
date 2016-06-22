package de.nedelosk.modularmachines.api.transport.node;

import de.nedelosk.modularmachines.api.transport.ITransportPart;
import net.minecraft.util.EnumFacing;

public interface ITransportNode extends ITransportPart {

	INodeType getType();

	void update();

	@Override
	INodeSide[] getSides();

	@Override
	INodeSide getSide(EnumFacing direction);

	@Override
	INodeSide getSide(int direction);

	void setType(INodeType type);
}
