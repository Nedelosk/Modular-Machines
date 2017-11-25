package modularmachines.common.network;

import modularmachines.common.network.packets.IPacketHandlerClient;
import modularmachines.common.network.packets.IPacketHandlerServer;

public enum PacketId {
	UPDATE_MODULE,
	UPDATE_MODULE_CONTAINER,
	ADD_MODULE,
	REMOVE_MODULE;
	
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
