package nedelosk.forestday.common.network.packets.machines;

import io.netty.buffer.ByteBuf;
import nedelosk.forestday.common.machines.wood.workbench.TileWorkbench;
import nedelosk.forestday.common.machines.wood.workbench.TileWorkbench.Mode;
import nedelosk.forestday.common.network.packets.PacketTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSwitchMode extends PacketTileEntity<TileWorkbench> implements IMessageHandler<PacketSwitchMode, IMessage> {

	private Mode mode;
	
	public PacketSwitchMode() {
		super();
	}

	public PacketSwitchMode(TileWorkbench tile) {
		super(tile);
		this.mode = tile.getMode();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeShort((short) mode.ordinal());
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
	    short ordinal = buf.readShort();
	    mode = Mode.values()[ordinal];
	}
	
	@Override
	public IMessage onMessage(PacketSwitchMode message, MessageContext ctx) {
	    TileWorkbench tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
	    tile.setMode(message.mode);
	    ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}

}
