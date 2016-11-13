package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.network.DataInputStreamMM;

public interface IPacketClient extends IPacket {

	@SideOnly(Side.CLIENT)
	void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException;
}
