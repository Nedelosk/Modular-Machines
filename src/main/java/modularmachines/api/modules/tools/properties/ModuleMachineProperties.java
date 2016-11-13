package modularmachines.api.modules.tools.properties;

import net.minecraftforge.common.config.Configuration;

import modularmachines.api.modules.ModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.state.IModuleState;

public class ModuleMachineProperties extends ModuleProperties implements IModuleMachineProperties {

	protected double defaultMaxSpeed;
	protected final int defaultWorkTimeModifier;
	protected double maxSpeed;
	protected int workTimeModifier;

	public ModuleMachineProperties(int complexity, int workTimeModifier, double maxSpeed) {
		super(complexity);
		this.defaultWorkTimeModifier = workTimeModifier;
		this.defaultMaxSpeed = maxSpeed;
		this.maxSpeed = defaultMaxSpeed;
		this.workTimeModifier = defaultWorkTimeModifier;
	}

	public ModuleMachineProperties(int complexity, int workTimeModifier) {
		this(complexity, workTimeModifier, 0);
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
		maxSpeed = getDouble(config, "maxSpeed", "modules." + container.getItemContainer().getRegistryName(), defaultMaxSpeed, 0.1D, 2.0D, "");
		workTimeModifier = config.getInt("workTimeModifier", "modules." + container.getItemContainer().getRegistryName(), defaultWorkTimeModifier, 0, 100, "");
	}
}
