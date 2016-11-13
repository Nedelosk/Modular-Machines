package modularmachines.api.modules.tools.properties;

import net.minecraftforge.common.config.Configuration;

import modularmachines.api.modules.ModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.state.IModuleState;

public class ModuleBoilerProperties extends ModuleProperties implements IModuleBoilerProperties {

	protected int waterPerWork;
	protected final int defaultWaterPerWork;

	public ModuleBoilerProperties(int complexity, int waterPerWork) {
		super(complexity);
		this.defaultWaterPerWork = waterPerWork;
		this.waterPerWork = waterPerWork;
	}

	@Override
	public int getWaterPerWork(IModuleState state) {
		return waterPerWork;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		waterPerWork = config.getInt("waterPerWork", "modules." + container.getItemContainer().getRegistryName(), defaultWaterPerWork, 0, 35, "");
	}
}
