package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;

import modularmachines.common.network.PacketBufferMM;

public interface IPacketHandlerServer extends IPacketHandler {
	void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException;
}
