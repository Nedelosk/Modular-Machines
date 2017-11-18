package modularmachines.common.network.packets;

import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import modularmachines.common.network.PacketId;

public interface IPacket {
	
	FMLProxyPacket getPacket();
	
	PacketId getPacketId();
}
