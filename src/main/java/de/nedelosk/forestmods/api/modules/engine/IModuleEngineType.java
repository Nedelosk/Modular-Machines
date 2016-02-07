package de.nedelosk.forestmods.api.modules.engine;

import de.nedelosk.forestmods.api.modules.IModuleType;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleEngineType extends IModuleType {

	int getSpeedModifier(ModuleStack stack);
}
