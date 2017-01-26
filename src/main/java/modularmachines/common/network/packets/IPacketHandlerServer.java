package modularmachines.common.network.packets;

import java.io.IOException;

import modularmachines.common.network.PacketBufferMM;
import net.minecraft.entity.player.EntityPlayerMP;

public interface IPacketHandlerServer extends IPacketHandler {
	void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException;
}
