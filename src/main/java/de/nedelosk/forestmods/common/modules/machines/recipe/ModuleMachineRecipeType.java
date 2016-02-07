package de.nedelosk.forestmods.common.modules.machines.recipe;

import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeType;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public class ModuleMachineRecipeType implements IModuleMachineRecipeType {

	private final int speed;

	public ModuleMachineRecipeType(int speed) {
		this.speed = speed;
	}

	@Override
	public int getSpeed(ModuleStack stack) {
		return speed;
	}
}
