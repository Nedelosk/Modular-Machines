package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSelectAssemblerPosition extends PacketModularHandler implements IMessageHandler<PacketSelectAssemblerPosition, IMessage> {

	public EnumStoragePosition position;

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		position = EnumStoragePosition.values()[buf.readShort()];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeShort(position.ordinal());
	}

	public PacketSelectAssemblerPosition() {
	}

	public PacketSelectAssemblerPosition(IModularHandler handler, EnumStoragePosition position) {
		super(handler);
		this.position = position;
	}

	@Override
	public IMessage onMessage(PacketSelectAssemblerPosition message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		IModularHandler modularHandler = message.getModularHandler(ctx);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getAssembler() != null && !modularHandler.isAssembled()){
			((IPositionedModularAssembler)modularHandler.getAssembler()).setSelectedPosition(message.position);
		}
		entityPlayerMP.openGui(ModularMachines.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return null;
	}
}
