package nedelosk.modularmachines.plugins.tconstruct;

import nedelosk.forestcore.api.plugins.Plugin;
import nedelosk.modularmachines.common.config.ModularConfig;

public class PluginTConstruct extends Plugin {

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}

	@Override
	public String getRequiredMod() {
		return "TConstruct";
	}

	@Override
	public boolean getConfigOption() {
		return ModularConfig.pluginTinkers;
	}

}
