package nedelosk.modularmachines.plugins.mt;

import minetweaker.MineTweakerAPI;
import nedelosk.forestday.plugins.basic.Plugin;
import nedelosk.modularmachines.common.config.ModularConfig;

public class PluginMineTweaker3 extends Plugin {

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
		return ModularConfig.pluginMineTweaker3;
	}

}
