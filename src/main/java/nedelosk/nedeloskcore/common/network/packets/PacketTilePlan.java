package nedelosk.nedeloskcore.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;

public class PacketTilePlan extends PacketTileEntity<TilePlan> implements IMessageHandler<PacketTilePlan, IMessage> {

	public boolean closedGui;
	
	public PacketTilePlan() {
		super();
	}

	public PacketTilePlan(TilePlan tile) {
		super(tile);
		this.closedGui = tile.closedGui;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeShort((short) ((closedGui == true) ? 1 : 0));
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
	    short ordinal = buf.readShort();
	    closedGui = ((ordinal == 1) ? true : false);
	}
	
	@Override
	public IMessage onMessage(PacketTilePlan message, MessageContext ctx) {
		message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj).closedGui = message.closedGui;
		return null;
	}
	
}
