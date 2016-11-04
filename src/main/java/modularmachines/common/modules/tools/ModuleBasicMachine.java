package modularmachines.common.modules.tools;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.EnumToolType;
import modularmachines.api.modules.tools.ModuleMachine;
import modularmachines.common.modules.pages.ControllerPage;
import modularmachines.common.modules.pages.MainPage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHeatBuffer;
import modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.world.WorldServer;

public abstract class ModuleBasicMachine extends ModuleMachine {

	public ModuleBasicMachine(String name) {
		super(name);
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			if (getType(state) == EnumToolType.HEAT) {
				PacketHandler.sendToNetwork(new PacketSyncHeatBuffer(handler), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
			}
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	protected IModulePage getControllerPage(IModuleState state) {
		return new ControllerPage(state);
	}

	@Override
	protected Class<? extends IModulePage> getMainPageClass() {
		return MainPage.class;
	}
}
