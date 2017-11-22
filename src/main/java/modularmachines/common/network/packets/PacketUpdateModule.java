package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.pages.PageComponent;
import modularmachines.common.network.IStreamable;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;

public class PacketUpdateModule extends PacketModule {
	
	public PacketUpdateModule() {
	}
	
	public PacketUpdateModule(IModule module) {
		super(module);
	}
	
	public PacketUpdateModule(IModule module, PageComponent page) {
		super(module, page);
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		if (index > 0) {
			IModule module = source.getModule(index);
			if (componentIndex > 0) {
				PageComponent page = module.getComponent(componentIndex);
				if (page instanceof IStreamable) {
					((IStreamable) page).writeData(data);
				}
			} else {
				if (module instanceof IStreamable) {
					((IStreamable) module).writeData(data);
				}
			}
		}
	}
	
	public static final class Handler implements IPacketHandlerClient {
		
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			IModuleContainer container = PacketLocatable.getContainer(data, player.world);
			int index = data.readInt();
			int componentIndex = data.readInt();
			if (index > 0) {
				IModule module = container.getModule(index);
				if (module == null) {
					return;
				}
				if (componentIndex > 0) {
					PageComponent page = module.getComponent(componentIndex);
					if (page instanceof IStreamable) {
						((IStreamable) page).readData(data);
					}
				} else if (module instanceof IStreamable) {
					((IStreamable) module).readData(data);
				}
			}
		}
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.UPDATE_MODULE;
	}
}
