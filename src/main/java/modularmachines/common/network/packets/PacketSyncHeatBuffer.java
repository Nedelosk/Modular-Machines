package modularmachines.common.network.packets;

/*public class PacketSyncHeatBuffer extends PacketLocatable {

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

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		if (modularHandler != null && modularHandler.getModular() != null) {
			BlockPos pos = ((IModularHandlerTileEntity) modularHandler).getPos();
			modularHandler.getModular().getHeatSource().setHeatStored(heatBuffer);
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_HEAT;
	}
}*/
