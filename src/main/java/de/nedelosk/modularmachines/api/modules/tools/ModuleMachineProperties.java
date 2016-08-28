package de.nedelosk.modularmachines.api.modules.tools;

import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.common.config.Configuration;

public class ModuleMachineProperties extends ModuleProperties implements IModuleMachineProperties {

	protected final float defaultMaxSpeed;
	protected final int defaultWorkTimeModifier;
	protected float maxSpeed;
	protected int workTimeModifier;

	public ModuleMachineProperties(int complexity, EnumModuleSize size, int workTimeModifier, float maxSpeed) {
		super(complexity, size);
		this.defaultWorkTimeModifier = workTimeModifier;
		this.defaultMaxSpeed = maxSpeed;
		this.maxSpeed = defaultMaxSpeed;
		this.workTimeModifier = defaultWorkTimeModifier;
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

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		maxSpeed = config.getFloat("maxSpeed", "modules." + container.getRegistryName(), defaultMaxSpeed, 0.1F, 2.0F, "");
		workTimeModifier = config.getInt("workTimeModifier", "modules." + container.getRegistryName(), defaultWorkTimeModifier, 0, 100, "");
	}
}
