package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModulePosition;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;

public class PacketInjectModule extends PacketModuleProvider {
	public PacketInjectModule() {
	}
	
	public PacketInjectModule(IModuleContainer provider, int index, int positionIndex, ItemStack itemStack) {
		super(provider, index, positionIndex, itemStack);
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.ADD_MODULE;
	}
	
	public static final class Handler implements IPacketHandlerClient {
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			IModuleContainer container = getContainer(data, player.world);
			int handlerIndex = data.readVarInt();
			int positionIndex = data.readVarInt();
			ItemStack itemStack = data.readItemStack();
			if (container != null) {
				IModuleHandler handler = null;
				if (handlerIndex == -1) {
					handler = container.getHandler();
				} else {
					Module module = container.getModule(handlerIndex);
					if (module instanceof IModuleProvider) {
						IModuleProvider provider = (IModuleProvider) module;
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
				IModuleDataContainer dataContainer = ModuleHelper.getContainerFromItem(itemStack);
				if (dataContainer == null) {
					return;
				}
				if (!handler.insertModule(position, dataContainer, itemStack, false)) {
					return;
				}
			}
		}
	}
}
