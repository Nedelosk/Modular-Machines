package nedelosk.forestday.common.plugins.minetweaker;

import minetweaker.MineTweakerAPI;
import nedelosk.forestday.common.plugins.minetweaker.handler.WorkbenchRecipeHandler;
import nedelosk.nedeloskcore.plugins.Plugin;

public class PluginMineTweaker extends Plugin {

	@Override
	public void preInit() {
		MineTweakerAPI.registerClass(WorkbenchRecipeHandler.class);
	}
	
	@Override
	public String getRequiredMod() {
		return "MineTweaker3";
	}
	
}
