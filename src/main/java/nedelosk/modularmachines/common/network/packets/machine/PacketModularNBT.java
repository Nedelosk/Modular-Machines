package nedelosk.modularmachines.common.network.packets.machine;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.nedeloskcore.common.network.packets.PacketTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularNBT extends PacketTileEntity<TileModular> implements IMessageHandler<PacketModularNBT, IMessage>{

	public NBTTagCompound nbt;
	
	public PacketModularNBT() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		nbt = ByteBufUtils.readTag(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeTag(buf, nbt);
	}

	public PacketModularNBT(TileModular tile) {
		super(tile);
		nbt = new NBTTagCompound();
		tile.writeToNBT(nbt);
	}
	
	@Override
	public IMessage onMessage(PacketModularNBT message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileModular tile = message.getTileEntity(world);
		
		tile.readFromNBT(message.nbt);
		world.markBlockForUpdate(message.x, message.y, message.z);
		return null;
	}

}
