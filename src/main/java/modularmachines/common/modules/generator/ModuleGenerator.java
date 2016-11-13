package modularmachines.common.modules.generator;

import net.minecraft.world.WorldServer;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.ITickable;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePositioned;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;

public abstract class ModuleGenerator extends Module implements ITickable, IModulePositioned {

	public ModuleGenerator(String name) {
		super("generator." + name);
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[] { EnumModulePositions.SIDE };
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
	}

	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}
}
