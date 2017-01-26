package modularmachines.common.network.packets;

import modularmachines.common.network.PacketId;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public interface IPacket {

	FMLProxyPacket getPacket();

	PacketId getPacketId();
}
