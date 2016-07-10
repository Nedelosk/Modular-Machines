package de.nedelosk.modularmachines.api.modular;

import java.util.Map;

import de.nedelosk.modularmachines.api.modules.IModuleState;

public interface IModuleIndexStorage {

	Map<IModuleState, Integer> getStateToSlotIndex();

	Map<Integer, IModuleState> getSlotIndexToState();
}
