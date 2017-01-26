package modularmachines.common.network.packets;

import java.io.IOException;

import modularmachines.common.network.PacketBufferMM;
import net.minecraft.entity.player.EntityPlayer;

public interface IPacketHandlerClient extends IPacketHandler {
	void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException;
}
