package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.managers.fluids.IModuleTankManager.TankMode;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PacketTankManager extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketTankManager, IMessage> {

	private int ID;
	private String module = null;
	private TankMode mode = null;
	private ForgeDirection direction = null;
	private int capacity = -1;

	public PacketTankManager() {
	}

	public PacketTankManager(TileEntity tile, TankMode mode, int ID) {
		super(tile);
		this.mode = mode;
		this.ID = ID;
	}

	public PacketTankManager(TileEntity tile, String module, int ID) {
		super(tile);
		this.module = module;
		this.ID = ID;
	}

	public PacketTankManager(TileEntity tile, ForgeDirection direction, int ID) {
		super(tile);
		this.direction = direction;
		this.ID = ID;
	}

	public PacketTankManager(TileEntity tile, int capacity, int ID) {
		super(tile);
		this.capacity = capacity;
		this.ID = ID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		ID = buf.readInt();
		int position = buf.readInt();
		if (position == 0) {
			mode = TankMode.values()[buf.readShort()];
		} else if (position == 1) {
			module = ByteBufUtils.readUTF8String(buf);
		} else if (position == 2) {
			direction = ForgeDirection.values()[buf.readShort()];
		} else if (position == 3) {
			capacity = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(ID);
		if (mode != null) {
			buf.writeInt(0);
			buf.writeShort(mode.ordinal());
		} else if (module != null) {
			buf.writeInt(1);
			ByteBufUtils.writeUTF8String(buf, module);
		} else if (direction != null) {
			buf.writeInt(2);
			buf.writeShort(direction.ordinal());
		} else if (capacity != -1) {
			buf.writeInt(3);
			buf.writeInt(capacity);
		} else {
			buf.writeInt(4);
		}
	}

	@Override
	public IMessage onMessage(PacketTankManager message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		if (message.mode != null) {
			ModularUtils.getTankManager(tile.getModular()).getSaver().getData(message.ID).setMode(message.mode);
		} else if (message.module != null) {
			ModularUtils.getTankManager(tile.getModular()).getSaver().getData(message.ID).setModule(message.module);
		} else if (message.direction != null) {
			ModularUtils.getTankManager(tile.getModular()).getSaver().getData(message.ID).setDirection(message.direction);
		} else if (message.capacity != -1) {
			ModularUtils.getTankManager(tile.getModular()).getSaver().getData(message.ID).setCapacity(message.capacity);
			ModularUtils.getTankManager(tile.getModular()).getSaver().setUnusedCapacity(-message.capacity);
		}
		world.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}
}
