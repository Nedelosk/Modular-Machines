package nedelosk.nedeloskcore.common.network.handler;

import nedelosk.nedeloskcore.common.network.packets.PacketTilePlan;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTilePlanHandler implements IMessageHandler<PacketTilePlan, IMessage> {

	@Override
	public IMessage onMessage(PacketTilePlan message, MessageContext ctx) {
		message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj).closedGui = message.closedGui;
		return null;
	}

}
