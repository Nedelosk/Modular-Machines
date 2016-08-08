package de.nedelosk.modularmachines.api.modular;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;

public interface IPositionedModularAssembler extends IModularAssembler {

	EnumPosition getSelectedPosition();

	void setSelectedPosition(EnumPosition position);

	IAssemblerLogic getLogic(EnumPosition pos);

	@Override
	IPositionedModularAssembler copy(IModularHandler handler);
}
