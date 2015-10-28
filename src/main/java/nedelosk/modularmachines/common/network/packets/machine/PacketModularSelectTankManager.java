package nedelosk.modularmachines.common.network.packets.machine;

import io.netty.buffer.ByteBuf;
import nedelosk.forestday.common.network.packets.PacketTileEntity;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularSelectTankManager extends PacketTileEntity<TileModular>
		implements IMessageHandler<PacketModularSelectTankManager, IMessage> {

	public int ID;
	public ForgeDirection direction;
	public int priority = -1;
	public Fluid fluid;

	public PacketModularSelectTankManager() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		ID = buf.readInt();
		int position = buf.readInt();
		if (position == 0) {
			fluid = FluidRegistry.getFluid(buf.readInt());
		} else if (position == 1) {
			direction = ForgeDirection.values()[buf.readInt()];
		} else if (position == 2) {
			priority = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(ID);
		if (fluid != null) {
			buf.writeInt(0);
			buf.writeInt(fluid.getID());
		} else if (direction != null) {
			buf.writeInt(1);
			buf.writeInt(direction.ordinal());
		} else if (priority != -1) {
			buf.writeInt(2);
			buf.writeInt(priority);
		} else if (fluid == null) {
			buf.writeInt(3);
		}
	}

	public PacketModularSelectTankManager(TileModular tile, Fluid fluid, int ID) {
		super(tile);
		this.fluid = fluid;
		this.ID = ID;
	}

	public PacketModularSelectTankManager(TileModular tile, ForgeDirection direction, int ID) {
		super(tile);
		this.direction = direction;
		this.ID = ID;
	}

	public PacketModularSelectTankManager(TileModular tile, int priority, int ID) {
		super(tile);
		this.priority = priority;
		this.ID = ID;
	}

	@Override
	public IMessage onMessage(PacketModularSelectTankManager message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileModular tile = message.getTileEntity(world);
		if (message.fluid != null) {
			tile.getModular().getTankManeger().getProducer().getManager().setFilter(message.fluid, message.ID);
		} else if (message.direction != null) {
			tile.getModular().getTankManeger().getProducer().getManager().setDirection(message.direction, message.ID);
		} else if (message.priority != -1) {
			tile.getModular().getTankManeger().getProducer().getManager().setPriority(message.priority, message.ID);
		} else if (message.fluid == null) {
			tile.getModular().getTankManeger().getProducer().getManager().setFilter(null, message.ID);
		}
		world.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}

}
