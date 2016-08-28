package de.nedelosk.modularmachines.api.modular;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;

public interface IPositionedModularAssembler extends IModularAssembler {

	EnumStoragePosition getSelectedPosition();

	void setSelectedPosition(EnumStoragePosition position);

	IAssemblerLogic getLogic(EnumStoragePosition pos);

	@Override
	IPositionedModularAssembler copy(IModularHandler handler);
}
