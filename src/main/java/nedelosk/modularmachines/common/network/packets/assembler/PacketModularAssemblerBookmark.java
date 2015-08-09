package nedelosk.modularmachines.common.network.packets.assembler;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.nedeloskcore.common.network.packets.PacketTileEntity;

import java.nio.charset.StandardCharsets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularAssemblerBookmark extends PacketTileEntity<TileModularAssembler> implements IMessageHandler<PacketModularAssemblerBookmark, IMessage> {

	public String page;
	
	public PacketModularAssemblerBookmark() {
		super();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		//page = ByteBufUtils.readUTF8String(buf);
		try
		{
		byte[] bytes = new byte[buf.readInt()];
		buf.readBytes(bytes);
		page = new String(bytes, StandardCharsets.UTF_8);
		}catch(Exception e){
			
		}

	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		//ByteBufUtils.writeUTF8String(buf, page);
		buf.writeInt(page.getBytes().length);
		buf.writeBytes(page.getBytes());
	}

	public PacketModularAssemblerBookmark(TileModularAssembler tile, String page) {
		super(tile);
		this.page = page;
	}
	
	@Override
	public IMessage onMessage(PacketModularAssemblerBookmark message, MessageContext ctx) {
		TileModularAssembler tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
			tile.page = message.page;
		
		return null;
	}

}
