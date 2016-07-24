package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketModularAssembler extends PacketModularHandler implements IMessageHandler<PacketModularAssembler, IMessage> {

	private boolean isAssembled;

	public PacketModularAssembler() {
	}

	public PacketModularAssembler(IModularHandler handler, boolean isAssembled) {
		super(handler);
		this.isAssembled = isAssembled;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		isAssembled = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeBoolean(isAssembled);
	}

	@Override
	public IMessage onMessage(PacketModularAssembler message, MessageContext ctx) {
		IModularHandler handler = message.getModularHandler(ctx);
		handler.setAssembled(message.isAssembled);
		if(message.isAssembled){
			try {
				handler.setModular(handler.getAssembler().assemble());
			} catch (AssemblerException e) {
				e.printStackTrace();
			}
			handler.setAssembler(null);
		}else{
			handler.setAssembler(handler.getModular().disassemble());
			handler.setModular(null);
		}
		BlockPos pos = getPos(handler);
		ctx.getServerHandler().playerEntity.openGui(ModularMachines.instance, 0, ctx.getServerHandler().playerEntity.worldObj, pos.getX(), pos.getY(), pos.getZ());
		return null;
	}


}
