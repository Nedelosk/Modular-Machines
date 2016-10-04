package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import net.minecraft.entity.player.EntityPlayer;

public interface IPacketClient extends IPacket{

	void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException;
}
