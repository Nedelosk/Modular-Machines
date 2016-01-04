package nedelosk.modularmachines.plugins.mt;

import minetweaker.MineTweakerAPI;
import nedelosk.forestcore.library.plugins.APlugin;
import nedelosk.modularmachines.common.config.Config;

public class PluginMineTweaker3 extends APlugin {

	@Override
	public void preInit() {
		MineTweakerAPI.registerClass(ModularRecipeHandler.class);
	}

	@Override
	public String getRequiredMod() {
		return "MineTweaker3";
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginMineTweaker3;
	}

}
