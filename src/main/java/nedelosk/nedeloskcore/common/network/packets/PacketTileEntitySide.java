package nedelosk.nedeloskcore.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;

public abstract class PacketTileEntitySide<T extends TileEntity> extends PacketTileEntity<T> implements IMessageHandler {

	public PacketTileEntitySide() {
		
	}
	
	public PacketTileEntitySide(T tile) {
		super(tile);
	}

	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx) {
	    if(!(message instanceof PacketTileEntitySide))
	        return null;

	    PacketTileEntitySide packet = (PacketTileEntitySide) message;
	      
		if(ctx.side == Side.CLIENT)
			packet.handleClientSide(ctx.getClientHandler());
		else 
			packet.handleServerSide(ctx.getServerHandler());
		return null;
	}
	
	public abstract void handleClientSide(NetHandlerPlayClient netHandler);
	public abstract void handleServerSide(NetHandlerPlayServer netHandler);
	
}
