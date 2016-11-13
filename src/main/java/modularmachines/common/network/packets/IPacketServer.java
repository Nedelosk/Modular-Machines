package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;

import modularmachines.api.modules.network.DataInputStreamMM;

public interface IPacketServer extends IPacket {

	void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException;
}
