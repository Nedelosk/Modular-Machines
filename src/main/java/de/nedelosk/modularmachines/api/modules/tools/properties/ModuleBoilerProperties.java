package de.nedelosk.modularmachines.api.modules.tools.properties;

import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.common.config.Configuration;

public class ModuleBoilerProperties extends ModuleProperties implements IModuleBoilerProperties {

	protected int waterPerWork;
	protected final int defaultWaterPerWork;

	public ModuleBoilerProperties(int complexity, EnumModuleSize size, int waterPerWork) {
		super(complexity, size);

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
		waterPerWork = config.getInt("waterPerWork", "modules." + container.getRegistryName(), defaultWaterPerWork, 0, 35, "");
	}
}
