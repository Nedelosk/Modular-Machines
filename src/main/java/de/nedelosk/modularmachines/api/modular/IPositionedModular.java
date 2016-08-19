package de.nedelosk.modularmachines.api.modular;

import java.util.Collection;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;

public interface IPositionedModular extends IModular {

	void setModuleStorage(EnumStoragePosition position, IPositionedModuleStorage storage);

	IPositionedModuleStorage getModuleStorage(EnumStoragePosition position);

	Collection<IPositionedModuleStorage> getModuleStorages();

	@Override
	IPositionedModularAssembler disassemble();

	@Override
	IPositionedModular copy(IModularHandler handler);

}
