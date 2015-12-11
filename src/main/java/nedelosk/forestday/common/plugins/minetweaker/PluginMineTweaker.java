package nedelosk.forestday.common.plugins.minetweaker;

import minetweaker.MineTweakerAPI;
import nedelosk.forestcore.api.plugins.APlugin;
import nedelosk.forestday.common.plugins.minetweaker.handler.WorkbenchRecipeHandler;

public class PluginMineTweaker extends APlugin {

	@Override
	public void preInit() {
		MineTweakerAPI.registerClass(WorkbenchRecipeHandler.class);
	}

	@Override
	public String getRequiredMod() {
		return "MineTweaker3";
	}

}
