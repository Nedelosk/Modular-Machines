package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;

public class PacketSyncHeatBuffer extends PacketModularHandler {

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
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		if(modularHandler != null && modularHandler.getModular() != null){
			BlockPos pos = ((IModularHandlerTileEntity)modularHandler).getPos();
			modularHandler.getModular().getHeatSource().setHeatStored(heatBuffer);
		}
	}

	@Override
	void handleServerSafe(NetHandlerPlayServer netHandler) {
	}

}
