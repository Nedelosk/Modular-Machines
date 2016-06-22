package de.nedelosk.modularmachines.api.transport.node;

import net.minecraft.item.ItemStack;

public interface IContentHandler<H> {

	void tick();

	void update();

	boolean canConnectToSide(INodeSide side);
	
	boolean insertItem(ItemStack stack);

	boolean extractItem();

	H getHandler(INodeSide side);
	
	Class<H> getHandlerClass();
}
