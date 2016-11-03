package de.nedelosk.modularmachines.api.modules.transport;

import java.util.List;

import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface ITransportHandler<H, T extends ITransportCycle<H>> {

	void work(IModuleState<IModuleTansport> moduleState, int ticks);

	ITransportCyclePage<H, T> createCyclePage(IModuleState<IModuleTansport> transportModule, @Nullable T cycle);

	List<T> getCycles(IModuleState<IModuleTansport> moduleState);

	List<ITransportHandlerWrapper<H>> getHandlers(IModuleState<IModuleTansport> moduleState);

	Class<? extends H> getHandlerClass();

	int getAllowedComplexity();
}
