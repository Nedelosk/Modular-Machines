package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.PacketId;

public class PacketModuleCleaner extends PacketModule {

	public PacketModuleCleaner() {
	}

	public PacketModuleCleaner(Module module) {
		super(module);
	}
	

	@Override
	public PacketId getPacketId() {
		return PacketId.MODULE_CLEANER;
	}
	
	public static final class Handler implements IPacketHandlerClient, IPacketHandlerServer{

		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException {
			Container container = player.openContainer;
			if (container instanceof ContainerModular) {
				IModularHandler handler = getModularHandler(player);
				IModuleState state = handler.getModular().getModule(index);
				((IModuleModuleCleaner) state.getModule()).cleanModule(state);
				PacketHandler.sendToNetwork(this, ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) player.worldObj);
			}
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			Container container = player.openContainer;
			if (container instanceof ContainerModular) {
				IModularHandler handler = getModularHandler(player);
				IModuleState state = handler.getModular().getModule(index);
				((IModuleModuleCleaner) state.getModule()).cleanModule(state);
			}
		}
		
	}
}
