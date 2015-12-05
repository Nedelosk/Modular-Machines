package nedelosk.modularmachines.api.packets;

import io.netty.buffer.ByteBuf;
import nedelosk.forestday.api.packets.PacketTileEntity;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.producers.fluids.ITankManager.TankMode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTankManager extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketTankManager, IMessage> {

	private int ID;
	private int producer = -1;
	private TankMode mode;
	private ForgeDirection direction;

	public PacketTankManager() {
	}
	
	public PacketTankManager(TileEntity tile, TankMode mode, int ID) {
		super(tile);
		this.mode = mode;
		this.ID = ID;
	}

	public PacketTankManager(TileEntity tile, int producer, int ID) {
		super(tile);
		this.producer = producer;
		this.ID = ID;
	}
	
	public PacketTankManager(TileEntity tile, ForgeDirection direction, int ID) {
		super(tile);
		this.direction = direction;
		this.ID = ID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		ID = buf.readInt();
		int position = buf.readInt();
		if (position == 0) {
			producer = buf.readInt();
		} else if (position == 1) {
			mode = TankMode.values()[buf.readShort()];
		} else if (position == 2) {
			direction = ForgeDirection.values()[buf.readShort()];
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(ID);
		if (mode != null) {
			buf.writeInt(0);
			buf.writeShort(mode.ordinal());
		} else if (producer != -1) {
			buf.writeInt(1);
			buf.writeInt(producer);
		} else if (direction != null) {
			buf.writeInt(2);
			buf.writeShort(direction.ordinal());
		} else {
			buf.writeInt(3);
		}
	}

	@Override
	public IMessage onMessage(PacketTankManager message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		if (message.mode != null) {
			tile.getModular().getTankManeger().getProducer().getManager().setTankMode(message.ID, message.mode);
		} else if (message.producer != -1) {
			tile.getModular().getTankManeger().getProducer().getManager().setProducer(message.ID, message.producer);
		} else if (message.direction != null) {
			tile.getModular().getTankManeger().getProducer().getManager().setDirection(message.ID, message.direction);
		}
		world.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}

}
