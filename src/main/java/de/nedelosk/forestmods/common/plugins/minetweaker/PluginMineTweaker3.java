package de.nedelosk.forestmods.common.plugins.minetweaker;

import de.nedelosk.forestcore.plugins.APlugin;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.plugins.minetweaker.handler.WorkbenchRecipeHandler;
import minetweaker.MineTweakerAPI;

public class PluginMineTweaker3 extends APlugin {

	@Override
	public void preInit() {
		MineTweakerAPI.registerClass(WorkbenchRecipeHandler.class);
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
