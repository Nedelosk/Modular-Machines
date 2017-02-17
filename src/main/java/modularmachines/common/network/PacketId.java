package modularmachines.common.network;

import modularmachines.common.network.packets.IPacketHandlerClient;
import modularmachines.common.network.packets.IPacketHandlerServer;

public enum PacketId {
	MODULE_CLEANER, ASSEMBLER_POSITION, SELECT_PAGE, SYNC_HANDLER_STATE, SYNC_HEAT, SYNC_REDSTONE_MODE, SYNC_MODE, SYNC_PERMISSON, SYNC_MODULE, UPDATE_MODULE, ADD_CYCLE, REMOVE_CYCLE, ACTIVE_MODULE_BEE_LOGIC;

	public static final PacketId[] VALUES = values();
	private IPacketHandlerServer packetServer;
	private IPacketHandlerClient packetClient;

	public void setHandlerClient(IPacketHandlerClient packetClient) {
		this.packetClient = packetClient;
	}

	public void setHandlerServer(IPacketHandlerServer packetServer) {
		this.packetServer = packetServer;
	}

	public IPacketHandlerServer getServerHandler() {
		return packetServer;
	}

	public IPacketHandlerClient getClientHandler() {
		return packetClient;
	}
}
