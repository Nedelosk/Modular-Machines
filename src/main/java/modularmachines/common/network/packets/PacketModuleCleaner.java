package modularmachines.common.network.packets;

/*public class PacketModuleCleaner extends PacketModule {

	public PacketModuleCleaner() {
	}

	public PacketModuleCleaner(Module module) {
		super(module);
	}
	

	@Override
	public PacketId getPacketId() {
		return PacketId.MODULE_CLEANER;
	}
	
	public static final class Handler implements IPacketHandlerClient, IPacketHandlerServer{

		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException {
			Container container = player.openContainer;
			if (container instanceof ContainerModular) {
				IModularHandler handler = getModularHandler(player);
				IModuleState state = handler.getModular().getModule(index);
				((IModuleModuleCleaner) state.getModule()).cleanModule(state);
				PacketHandler.sendToNetwork(this, ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) player.worldObj);
			}
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			Container container = player.openContainer;
			if (container instanceof ContainerModular) {
				IModularHandler handler = getModularHandler(player);
				IModuleState state = handler.getModular().getModule(index);
				((IModuleModuleCleaner) state.getModule()).cleanModule(state);
			}
		}
		
	}
}*/
