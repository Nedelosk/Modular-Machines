package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ModuleUtil;

public class PacketUpdateModule extends PacketModule {
	private IModule module;
	
	public PacketUpdateModule(IModule module) {
		super(module);
		this.module = module;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		module.writeData(data);
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.UPDATE_MODULE;
	}
	
	public static class Handler implements IPacketHandlerClient {
		
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			World world = player.world;
			IModuleContainer provider = PacketLocatable.getContainer(data, world);
			IModule module = getModule(provider, data);
			if (module == null) {
				return;
			}
			module.readData(data);
			ModuleUtil.markForModelUpdate(module);
		}
		
	}
}
