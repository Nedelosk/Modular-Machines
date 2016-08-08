package de.nedelosk.modularmachines.api.modular;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;

public interface ISimpleModularAssembler extends IModularAssembler {

	IAssemblerLogic getLogic();

	@Override
	ISimpleModularAssembler copy(IModularHandler handler);

}
