package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.energy.HeatBuffer;
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

public class PacketSyncHeatBuffer extends PacketModularHandler implements IMessageHandler<PacketSyncHeatBuffer, IMessage> {
	
	public double heatBuffer;

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		heatBuffer = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeDouble(heatBuffer);
	}

	public PacketSyncHeatBuffer() {
	}

	public PacketSyncHeatBuffer(IModularHandler handler) {
		super(handler);
		
		heatBuffer = handler.getModular().getHeatSource().getHeatStored();
	}

	@Override
	public IMessage onMessage(PacketSyncHeatBuffer message, MessageContext ctx) {
		IModularHandler modularHandler = message.getModularHandler(ctx);
		if(modularHandler != null && modularHandler.getModular() != null){
			modularHandler.getModular().getHeatSource().setHeatStored(message.heatBuffer);
			modularHandler.markDirty();
		}
		return null;
	}
	
}
