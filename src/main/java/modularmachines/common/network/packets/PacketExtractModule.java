package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ModuleUtil;

public class PacketExtractModule extends PacketModuleProvider {
	public PacketExtractModule(IModuleContainer provider, int index, int positionIndex) {
		super(provider, index, positionIndex);
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.REMOVE_MODULE;
	}
	
	public static final class Handler implements IPacketHandlerClient {
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			IModuleContainer container = getContainer(data, player.world);
			int handlerIndex = data.readVarInt();
			int positionIndex = data.readVarInt();
			if (container != null) {
				IModuleHandler handler = null;
				if (handlerIndex == -1) {
					handler = container.getHandler();
				} else {
					IModule module = container.getModule(handlerIndex);
					IModuleProvider provider = ModuleUtil.getComponent(module, IModuleProvider.class);
					if (provider != null) {
						handler = provider.getHandler();
					}
				}
				if (handler == null) {
					return;
				}
				IModulePosition position = handler.getPosition(positionIndex);
				if (position == null) {
					return;
				}
				handler.extractModule(position, false);
			}
		}
	}
}
