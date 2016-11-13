package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import modularmachines.api.modules.network.DataInputStreamMM;

public interface IPacket {

	void readData(DataInputStreamMM data) throws IOException;

	FMLProxyPacket getPacket();

	PacketId getPacketId();
}
