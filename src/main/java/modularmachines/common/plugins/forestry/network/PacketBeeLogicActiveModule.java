package modularmachines.common.plugins.forestry.network;

import java.io.IOException;

import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.apiculture.BeekeepingLogic;
import forestry.core.network.DataInputStreamForestry;
import forestry.core.network.DataOutputStreamForestry;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.network.DataInputStreamMM;
import modularmachines.api.modules.network.DataOutputStreamMM;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.network.packets.IPacketClient;
import modularmachines.common.network.packets.PacketId;
import modularmachines.common.network.packets.PacketModule;
import net.minecraft.entity.player.EntityPlayer;

public class PacketBeeLogicActiveModule extends PacketModule implements IPacketClient {

	private BeekeepingLogic beekeepingLogic;

	public PacketBeeLogicActiveModule() {
	}

	public PacketBeeLogicActiveModule(IModuleState module, IBeeHousing beeHousing) {
		super(module);
		this.beekeepingLogic = (BeekeepingLogic) beeHousing.getBeekeepingLogic();
		this.index = ((IModuleContentHandler) beeHousing).getModuleState().getIndex();
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.ACTIVE_MODULE_BEE_LOGIC;
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		beekeepingLogic.writeData(new DataOutputStreamForestry(data));
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModuleState state = getModule(player);
		IBeeHousing beeHousing = state.getContentHandler(IBeeHousing.class);
		IBeekeepingLogic beekeepingLogic = beeHousing.getBeekeepingLogic();
		if (beekeepingLogic instanceof BeekeepingLogic) {
			((BeekeepingLogic) beekeepingLogic).readData(new DataInputStreamForestry(data));
		}
	}
}
