package de.nedelosk.forestmods.common.modules.engine;

import de.nedelosk.forestmods.api.modules.engine.IModuleEngineType;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public class ModuleEngineType implements IModuleEngineType {

	private final int speedModifier;

	public ModuleEngineType(int speedModifier) {
		this.speedModifier = speedModifier;
	}

	@Override
	public int getSpeedModifier(ModuleStack stack) {
		return speedModifier;
	}
}
