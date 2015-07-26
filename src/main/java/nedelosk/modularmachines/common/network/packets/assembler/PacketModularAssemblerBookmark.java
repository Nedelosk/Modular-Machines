package nedelosk.modularmachines.common.network.packets.assembler;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.nedeloskcore.common.network.packets.PacketTileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularAssemblerBookmark extends PacketTileEntity<TileModularAssenbler> implements IMessageHandler<PacketModularAssemblerBookmark, IMessage> {

	public String page;
	public boolean slotToAssembler;
	
	public PacketModularAssemblerBookmark() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		page = ByteBufUtils.readUTF8String(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeUTF8String(buf, page);
	}

	public PacketModularAssemblerBookmark(TileModularAssenbler tile, String page) {
		super(tile);
		this.page = page;
	}
	
	@Override
	public IMessage onMessage(PacketModularAssemblerBookmark message, MessageContext ctx) {
		TileModularAssenbler tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
			tile.page = message.page;
		
		return null;
	}

}
