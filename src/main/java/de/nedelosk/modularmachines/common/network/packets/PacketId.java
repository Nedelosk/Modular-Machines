package de.nedelosk.modularmachines.common.network.packets;

public enum PacketId {

	MODULE_CLEANER,
	SELECT_ASSEMBLER_POSITION,
	SELECT_MODULE,
	SELECT_PAGE,
	SYNC_HANDLER_STATE,
	SYNC_HEAT,
	SYNC_REDSTONE_MODE,
	SYNC_TOOL_MODE,
	SYNC_PERMISSON,
	SYNC_MODULE,
	UPDATE_MODULE;

	public static final PacketId[] VALUES = values();

	private IPacketServer packetServer;
	private IPacketClient packetClient;

	public void setPacketClient(IPacketClient packetClient) {
		this.packetClient = packetClient;
	}

	public void setPacketServer(IPacketServer packetServer) {
		this.packetServer = packetServer;
	}

	public IPacketServer getServerPacket(){
		return packetServer;
	}

	public IPacketClient getClientPacket(){
		return packetClient;
	}
}
