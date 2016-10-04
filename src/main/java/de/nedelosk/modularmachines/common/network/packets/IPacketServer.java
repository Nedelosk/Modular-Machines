package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import net.minecraft.entity.player.EntityPlayerMP;

public interface IPacketServer extends IPacket {

	void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException;
}
