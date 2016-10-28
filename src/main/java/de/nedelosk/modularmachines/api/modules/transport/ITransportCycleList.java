package de.nedelosk.modularmachines.api.modules.transport;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface ITransportCycleList extends IModuleContentHandler {

	List<ITransportCycle> getCycles();

	@Override
	default boolean isCleanable() {
		return false;
	}

	@Override
	default boolean isEmpty() {
		return false;
	}

	@Override
	default void cleanHandler(IModuleState state) {
	}
}
