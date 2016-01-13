package nedelosk.forestday.plugins.minetweaker;

import minetweaker.MineTweakerAPI;
import nedelosk.forestcore.library.plugins.APlugin;
import nedelosk.forestday.plugins.minetweaker.handler.WorkbenchRecipeHandler;

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
