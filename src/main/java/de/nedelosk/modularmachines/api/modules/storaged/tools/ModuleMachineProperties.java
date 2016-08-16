package de.nedelosk.modularmachines.api.modules.storaged.tools;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;

public class ModuleMachineProperties extends ModuleProperties implements IModuleMachineProperties {

	protected final float maxSpeed;
	protected final int workTimeModifier;

	public ModuleMachineProperties(int complexity, EnumModuleSize size, int workTimeModifier, float maxSpeed) {
		super(complexity, size);
		this.workTimeModifier = workTimeModifier;
		this.maxSpeed = maxSpeed;
	}

	public ModuleMachineProperties(int complexity, EnumModuleSize size, int workTimeModifier) {
		this(complexity, size, workTimeModifier, 0);
	}

	@Override
	public float getMaxSpeed(IModuleState state) {
		return maxSpeed;
	}

	@Override
	public int getWorkTimeModifier(IModuleState state) {
		return workTimeModifier;
	}
}
