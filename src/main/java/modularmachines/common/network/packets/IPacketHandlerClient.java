package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

import modularmachines.common.network.PacketBufferMM;

public interface IPacketHandlerClient extends IPacketHandler {
	void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException;
}
