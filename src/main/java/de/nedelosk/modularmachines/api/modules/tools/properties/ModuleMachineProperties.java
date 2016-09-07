package de.nedelosk.modularmachines.api.modules.tools.properties;

import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.common.config.Configuration;

public class ModuleMachineProperties extends ModuleProperties implements IModuleMachineProperties {

	protected double defaultMaxSpeed;
	protected final int defaultWorkTimeModifier;
	protected double maxSpeed;
	protected int workTimeModifier;

	public ModuleMachineProperties(int complexity, EnumModuleSize size, int workTimeModifier, double maxSpeed) {
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
	public double getMaxSpeed(IModuleState state) {
		return maxSpeed;
	}

	@Override
	public int getWorkTimeModifier(IModuleState state) {
		return workTimeModifier;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		maxSpeed = getDouble(config, "maxSpeed", "modules." + container.getRegistryName(), defaultMaxSpeed, 0.1D, 2.0D, "");
		workTimeModifier = config.getInt("workTimeModifier", "modules." + container.getRegistryName(), defaultWorkTimeModifier, 0, 100, "");
	}
}
