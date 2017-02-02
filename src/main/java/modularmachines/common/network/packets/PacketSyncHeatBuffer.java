package modularmachines.common.network.packets;

import java.io.IOException;

import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ModuleUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncHeatBuffer extends PacketLocatable {

	public double heatBuffer;

	public PacketSyncHeatBuffer() {
	}

	public PacketSyncHeatBuffer(IModuleLogic logic) {
		super(logic);
		IHeatSource heatSource = ModuleUtil.getHeat(logic);
		heatBuffer = heatSource.getHeatStored();
	}

	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeDouble(heatBuffer);
	}

	public static final class Handler implements IPacketHandlerClient{
		
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			IModuleLogic logic = getLogic(data, player.world);
			if (logic != null) {
				IHeatSource heatSource = ModuleUtil.getHeat(logic);
				heatSource.setHeatStored(data.readInt());
			}
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_HEAT;
	}
}
