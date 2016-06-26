package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSelectModule extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectModule, IMessage> {

	public int index;

	public PacketSelectModule() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(index);
	}

	public PacketSelectModule(TileEntity tile, IModuleState module) {
		this(tile, module.getIndex());
	}

	public PacketSelectModule(TileEntity tile, int index) {
		super(tile);
		this.index = index;
	}

	@Override
	public IMessage onMessage(PacketSelectModule message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularHandler tile = (IModularHandler) message.getTileEntity(world);
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		tile.getModular().setCurrentModuleState(tile.getModular().getModule(message.index));
		entityPlayerMP.openGui(ModularMachines.instance, 0, world, message.pos.getX(), message.pos.getY(), message.pos.getZ());
		return null;
	}
}
