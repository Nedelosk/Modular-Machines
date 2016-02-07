package de.nedelosk.forestmods.api.modules.machines.recipe;

import de.nedelosk.forestmods.api.modules.IModuleType;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleMachineRecipeType extends IModuleType {

	int getSpeed(ModuleStack stack);
}
