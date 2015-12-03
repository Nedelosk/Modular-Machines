package nedelosk.modularmachines.api.packets;

import io.netty.buffer.ByteBuf;
import nedelosk.forestday.api.packets.PacketTileEntity;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.producers.fluids.ITankManager.TankMode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSelectTankManager extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectTankManager, IMessage> {

	public int ID;
	public String producerName;
	public TankMode mode;

	public PacketSelectTankManager() {
	}
	
	public PacketSelectTankManager(TileEntity tile, TankMode mode, int ID) {
		super(tile);
		this.mode = mode;
		this.ID = ID;
	}

	public PacketSelectTankManager(TileEntity tile, String producerName, int ID) {
		super(tile);
		this.producerName = producerName;
		this.ID = ID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		ID = buf.readInt();
		int position = buf.readInt();
		if (position == 0) {
			producerName = ByteBufUtils.readUTF8String(buf);
		} else if (position == 1) {
			mode = TankMode.values()[buf.readShort()];
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(ID);
		if (mode != null) {
			buf.writeInt(0);
			buf.writeInt(mode.ordinal());
		} else if (producerName != null) {
			buf.writeInt(1);
			ByteBufUtils.writeUTF8String(buf, producerName);
		} else {
			buf.writeInt(2);
		}
	}

	@Override
	public IMessage onMessage(PacketSelectTankManager message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		if (message.mode != null) {
			tile.getModular().getTankManeger().getProducer().getManager().setTankMode(message.ID, message.mode);
		} else if (message.producerName != null) {
			tile.getModular().getTankManeger().getProducer().getManager().setProducer(message.ID, message.producerName);
		}
		world.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}

}
