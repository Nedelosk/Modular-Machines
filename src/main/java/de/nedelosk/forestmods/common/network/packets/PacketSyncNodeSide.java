package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.common.transport.node.TileEntityTransportNode;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.util.ForgeDirection;

public class PacketSyncNodeSide extends PacketTileEntity<TileEntityTransportNode> implements IMessageHandler<PacketSyncNodeSide, IMessage> {

	private ForgeDirection selectedSide;

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
		selectedSide = ForgeDirection.values()[ordinal];
	}

	@Override
	public IMessage onMessage(PacketSyncNodeSide message, MessageContext ctx) {
		TileEntityTransportNode tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
		tile.setSelectedSide(message.selectedSide);
		ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}
}
