package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ModuleUtil;

public class PacketSyncHeatBuffer extends PacketLocatable {

	public double heatBuffer;

	public PacketSyncHeatBuffer() {
	}

	public PacketSyncHeatBuffer(IModuleContainer provider) {
		super(provider);
		IHeatSource heatSource = ModuleUtil.getHeat(provider);
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
			IModuleContainer provider = getContainer(data, player.world);
			if (provider != null) {
				IHeatSource heatSource = ModuleUtil.getHeat(provider);
				heatSource.setHeatStored(data.readDouble());
			}
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_HEAT;
	}
}
