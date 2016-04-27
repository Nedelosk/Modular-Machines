package de.nedelosk.forestmods.library.transport.node;

public interface IContentHandler<H> {

	void tick();

	void update();

	boolean canConnectToSide(INodeSide side);

	H getHandler(INodeSide side);
}
