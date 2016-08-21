package de.nedelosk.modularmachines.common.network.packets;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class AbstractPacketHandler implements IMessageHandler<AbstractPacket, IMessage> {

	@Override
	public IMessage onMessage(AbstractPacket packet, MessageContext ctx) {
		if(ctx.side == Side.SERVER) {
			return packet.handleServer(ctx.getServerHandler());
		}
		else {
			return packet.handleClient(ctx.getClientHandler());
		}
	}
}
