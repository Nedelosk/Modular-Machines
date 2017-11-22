package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;

public class PacketSyncModuleContainer extends PacketLocatable<IModuleContainer> {
	public PacketSyncModuleContainer(IModuleContainer source) {
		super(source);
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_MODULE_CONTAINER;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		source.writeData(data);
	}
	
	public static final class Handler implements IPacketHandlerClient {
		
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			IModuleContainer container = getContainer(data, player.world);
			if (container != null) {
				container.readData(data);
			}
		}
	}
}
