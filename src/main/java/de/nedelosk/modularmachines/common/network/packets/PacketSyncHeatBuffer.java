package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.network.DataOutputStreamMM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class PacketSyncHeatBuffer extends PacketModularHandler implements IPacketClient {

	public double heatBuffer;

	public PacketSyncHeatBuffer() {
	}

	public PacketSyncHeatBuffer(IModularHandler handler) {
		super(handler);

		heatBuffer = handler.getModular().getHeatSource().getHeatStored();
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		super.readData(data);
		heatBuffer = data.readDouble();
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		data.writeDouble(heatBuffer);
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		if(modularHandler != null && modularHandler.getModular() != null){
			BlockPos pos = ((IModularHandlerTileEntity)modularHandler).getPos();
			modularHandler.getModular().getHeatSource().setHeatStored(heatBuffer);
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_HEAT;
	}

}
