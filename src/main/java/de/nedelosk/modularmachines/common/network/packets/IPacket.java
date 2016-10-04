package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public interface IPacket {

	void readData(DataInputStreamMM data) throws IOException;

	FMLProxyPacket getPacket();

	PacketId getPacketId();
}
