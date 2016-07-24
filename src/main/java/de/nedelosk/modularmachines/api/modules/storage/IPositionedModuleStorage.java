package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;

public interface IPositionedModuleStorage extends IModuleStorage{

	EnumPosition getPosition();

}
