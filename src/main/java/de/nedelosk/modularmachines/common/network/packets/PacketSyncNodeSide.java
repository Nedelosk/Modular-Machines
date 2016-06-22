package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.common.transport.node.TileEntityTransportNode;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncNodeSide extends PacketTileEntity<TileEntityTransportNode> implements IMessageHandler<PacketSyncNodeSide, IMessage> {

	private EnumFacing selectedSide;

	public PacketSyncNodeSide() {
	}

	public PacketSyncNodeSide(TileEntityTransportNode tile) {
		super(tile);
		this.selectedSide = tile.getSelectedSide();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeShort((short) selectedSide.ordinal());
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		short ordinal = buf.readShort();
		selectedSide = EnumFacing.VALUES[ordinal];
	}

	@Override
	public IMessage onMessage(PacketSyncNodeSide message, MessageContext ctx) {
		TileEntityTransportNode tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
		tile.setSelectedSide(message.selectedSide);
		tile.markDirty();
		return null;
	}
}
