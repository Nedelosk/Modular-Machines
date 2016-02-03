package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.common.blocks.tile.TileWorkbench;
import de.nedelosk.forestmods.common.blocks.tile.TileWorkbench.Mode;
import io.netty.buffer.ByteBuf;

public class PacketSwitchWorktableMode extends PacketTileEntity<TileWorkbench> implements IMessageHandler<PacketSwitchWorktableMode, IMessage> {

	private Mode mode;

	public PacketSwitchWorktableMode() {
		super();
	}

	public PacketSwitchWorktableMode(TileWorkbench tile) {
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
	public IMessage onMessage(PacketSwitchWorktableMode message, MessageContext ctx) {
		TileWorkbench tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
		tile.setMode(message.mode);
		ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}
}
