package de.nedelosk.forestmods.common.modules.producers.recipe;

import de.nedelosk.forestmods.api.utils.ModuleStack;

public class ModuleProducerRecipeType implements IModuleProducerRecipeType {

	private final int speed;

	public ModuleProducerRecipeType(int speed) {
		this.speed = speed;
	}

	@Override
	public int getSpeed(ModuleStack stack) {
		return speed;
	}
}
