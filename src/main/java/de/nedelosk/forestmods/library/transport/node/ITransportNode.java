package de.nedelosk.forestmods.library.transport.node;

import de.nedelosk.forestmods.library.transport.ITransportPart;
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
