package de.nedelosk.modularmachines.common.modules.transport;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.Module;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModulePosition;
import de.nedelosk.modularmachines.api.modules.transports.EnumTransportMode;
import de.nedelosk.modularmachines.api.modules.transports.IModuleTransport;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.world.WorldServer;

public class ModuleTransport extends Module implements IModuleTransport {

	protected int transportedAmount;

	public ModuleTransport(String name) {
		super(name);
	}

	@Override
	public EnumTransportMode getMode() {
		return null;
	}

	@Override
	public EnumModulePosition getPosition(IModuleContainer container) {
		return EnumModulePosition.INTERNAL;
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

}
