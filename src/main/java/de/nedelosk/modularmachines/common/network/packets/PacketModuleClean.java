package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModuleModuleCleaner;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.Container;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketModuleClean extends PacketModule implements IMessageHandler<PacketModuleClean, IMessage> {

	public PacketModuleClean() {
	}

	public PacketModuleClean(IModuleState module) {
		super(module.getModular().getHandler(), module);
	}

	public PacketModuleClean(IModularHandler handler, int index) {
		super(handler, index);
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		Container container = Minecraft.getMinecraft().thePlayer.openContainer;
		if(container instanceof ContainerModular) {
			IModularHandler handler = getModularHandler(netHandler);
			IModuleState state = handler.getModular().getModule(index);

			((IModuleModuleCleaner)state.getModule()).cleanModule(state);
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		Container container = netHandler.playerEntity.openContainer;
		if(container instanceof ContainerModular) {
			IModularHandler handler = getModularHandler(netHandler);
			IModuleState state = handler.getModular().getModule(index);

			((IModuleModuleCleaner)state.getModule()).cleanModule(state);

			PacketHandler.sendToNetwork(this, ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) netHandler.playerEntity.worldObj);
		}
	}

	@Override
	public IMessage onMessage(PacketModuleClean message, MessageContext ctx) {
		IModularHandler handler = message.getModularHandler(ctx.getServerHandler());
		IModuleState state = handler.getModular().getModule(message.index);

		((IModuleModuleCleaner)state.getModule()).cleanModule(state);
		return null;
	}
}
