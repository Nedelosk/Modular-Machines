package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModuleModuleCleaner;
import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketModuleCleaner extends PacketModule implements IPacketClient, IPacketServer {

	public PacketModuleCleaner() {
	}

	public PacketModuleCleaner(IModuleState module) {
		super(module);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException  {
		Container container = player.openContainer;
		if(container instanceof ContainerModular) {
			IModularHandler handler = getModularHandler(player);
			IModuleState state = handler.getModular().getModule(index);

			((IModuleModuleCleaner)state.getModule()).cleanModule(state);
		}
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException {
		Container container = player.openContainer;
		if(container instanceof ContainerModular) {
			IModularHandler handler = getModularHandler(player);
			IModuleState state = handler.getModular().getModule(index);

			((IModuleModuleCleaner)state.getModule()).cleanModule(state);

			PacketHandler.sendToNetwork(this, ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) player.worldObj);
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.MODULE_CLEANER;
	}
}
