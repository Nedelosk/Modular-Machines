package modularmachines.api.modules.transport;

import java.util.List;

import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.state.IModuleState;

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
