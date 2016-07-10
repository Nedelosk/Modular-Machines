package de.nedelosk.modularmachines.common.modules.transport;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;

public class ModuleTransportPump extends ModuleTransport{

	public ModuleTransportPump(int transportedAmount) {
		super(transportedAmount);
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			IModularHandlerTileEntity handlerTile = (IModularHandlerTileEntity) handler;
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		return pages;
	}
}
