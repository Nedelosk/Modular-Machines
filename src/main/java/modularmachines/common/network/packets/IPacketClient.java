package modularmachines.common.network.packets;

import java.io.IOException;

import modularmachines.api.modules.network.DataInputStreamMM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPacketClient extends IPacket {

	@SideOnly(Side.CLIENT)
	void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException;
}
