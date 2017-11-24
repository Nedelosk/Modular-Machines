package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ModuleUtil;

public class PacketInjectModule extends PacketModuleProvider {
	protected ItemStack itemStack;
	
	public PacketInjectModule(IModuleContainer provider, int index, int positionIndex, ItemStack itemStack) {
		super(provider, index, positionIndex);
		this.itemStack = itemStack;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeItemStack(itemStack);
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
				IModuleDataContainer dataContainer = ModuleManager.registry.getContainerFromItem(itemStack);
				if (dataContainer == null) {
					return;
				}
				handler.insertModule(position, dataContainer, itemStack, false);
			}
		}
	}
}
