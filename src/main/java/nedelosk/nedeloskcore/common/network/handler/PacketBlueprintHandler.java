package nedelosk.nedeloskcore.common.network.handler;

import nedelosk.nedeloskcore.common.network.packets.PacketBlueprint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketBlueprintHandler implements IMessageHandler<PacketBlueprint, IMessage> 
{
	@Override
	public IMessage onMessage(PacketBlueprint message, MessageContext ctx) {
	    ctx.getServerHandler().playerEntity.inventory.mainInventory[ctx.getServerHandler().playerEntity.inventory.currentItem] = message.blueprint;
		return null;
	}
}
