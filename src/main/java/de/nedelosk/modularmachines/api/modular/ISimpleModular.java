package de.nedelosk.modularmachines.api.modular;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storage.IAddableModuleStorage;

public interface ISimpleModular extends IModular, IAddableModuleStorage {

	@Override
	ISimpleModularAssembler disassemble();

	@Override
	ISimpleModular copy(IModularHandler handler);

}
