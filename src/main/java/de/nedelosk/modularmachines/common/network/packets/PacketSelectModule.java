package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSelectModule extends PacketModularHandler implements IMessageHandler<PacketSelectModule, IMessage> {

	public int index;

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

	public PacketSelectModule() {
	}

	public PacketSelectModule(IModularHandler handler, IModuleState module) {
		this(handler, module.getIndex());
	}

	public PacketSelectModule(IModularHandler handler, int index) {
		super(handler);
		this.index = index;
	}

	@Override
	public IMessage onMessage(PacketSelectModule message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		IModularHandler modularHandler = message.getModularHandler(ctx);
		BlockPos pos = getPos(modularHandler);

		modularHandler.getModular().setCurrentModuleState(modularHandler.getModular().getModule(message.index));
		entityPlayerMP.openGui(ModularMachines.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return null;
	}
}
