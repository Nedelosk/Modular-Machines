package de.nedelosk.modularmachines.api.modules.transport;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface ITransportHandler<H> {
	
	void work();
	
	ICyclePage<H> createCyclePage(IModuleState<IModuleTansport> transportModule, IPage parent);
	
	List<ITransportCycle<H>> getCycles(IModuleState<IModuleTansport> moduleState);
	
	List<H> getHandlers(IModuleState<IModuleTansport> moduleState);
	
	Class<? extends H> getHandlerClass();
	
	int getLevel();
	
}
