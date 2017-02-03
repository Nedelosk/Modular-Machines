package modularmachines.common.modules;

import java.util.List;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleHelper;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.Assembler;
import modularmachines.common.modules.logic.ModuleLogic;

public class ModuleHelper implements IModuleHelper {

	@Override
	public IModuleLogic createLogic(ILocatable locatable, List<IStoragePosition> positions) {
		return new ModuleLogic(locatable, positions);
	}

	@Override
	public IAssembler createAssembler(ILocatable locatable, List<IStoragePosition> positions) {
		return new Assembler(locatable, positions);
	}

}
