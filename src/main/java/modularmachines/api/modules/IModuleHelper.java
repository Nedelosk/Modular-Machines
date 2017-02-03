package modularmachines.api.modules;

import java.util.List;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStoragePosition;

public interface IModuleHelper {
	
	IModuleLogic createLogic(ILocatable locatable, List<IStoragePosition> positions);
	
	IAssembler createAssembler(ILocatable locatable, List<IStoragePosition> positions);
	
}
