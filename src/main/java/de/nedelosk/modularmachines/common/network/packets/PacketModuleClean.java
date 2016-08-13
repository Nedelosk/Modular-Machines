package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModuleModuleCleaner;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
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
	public IMessage onMessage(PacketModuleClean message, MessageContext ctx) {
		IModularHandler handler = message.getModularHandler(ctx);
		IModuleState state = handler.getModular().getModule(message.index);

		((IModuleModuleCleaner)state.getModule()).cleanModule(state);
		return null;
	}
}
