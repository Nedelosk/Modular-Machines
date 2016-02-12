package de.nedelosk.forestmods.api.transport.node;

import de.nedelosk.forestmods.api.transport.ITransportPart;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITransportNode extends ITransportPart {

	INodeType getType();

	void update();

	@Override
	INodeSide[] getSides();

	@Override
	INodeSide getSide(ForgeDirection direction);

	@Override
	INodeSide getSide(int direction);

	void setType(INodeType type);
}
