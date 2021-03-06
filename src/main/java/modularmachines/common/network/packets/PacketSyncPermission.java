package modularmachines.common.network.packets;

/*public class PacketSyncPermission extends PacketLocatable implements IPacketClient, IPacketServer {

	public boolean permission;
	public int moduleIndex;
	public int position;

	public PacketSyncPermission() {
		super();
	}

	public PacketSyncPermission(IModularHandler modularHandler, IModuleState<IModuleControlled> moduleState, IModuleState state) {
		super(modularHandler);
		this.moduleIndex = state.getPosition();
		this.permission = moduleState.getModule().getModuleControl(moduleState).hasPermission(state);
		this.position = moduleState.getPosition();
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		super.readData(data);
		permission = data.readBoolean();
		moduleIndex = data.readInt();
		position = data.readInt();
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		data.writeBoolean(permission);
		data.writeInt(moduleIndex);
		data.writeInt(position);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		BlockPos pos = getPos(modularHandler);
		if (modularHandler.getModular() != null && modularHandler.isAssembled()) {
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(position);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setPermission(modularHandler.getModular().getModule(moduleIndex), permission);
			}
		}
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		BlockPos pos = getPos(modularHandler);
		if (modularHandler.getModular() != null && modularHandler.isAssembled()) {
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(position);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setPermission(modularHandler.getModular().getModule(moduleIndex), permission);
			}
		}
		PacketHandler.sendToNetwork(this, pos, player.getServerWorld());
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_PERMISSON;
	}
}*/
