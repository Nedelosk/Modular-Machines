package nedelosk.forestday.common.plugins.minetweaker;

import minetweaker.MineTweakerAPI;
import nedelosk.forestday.common.plugins.minetweaker.handler.AlloySmelterRecipeHandler;
import nedelosk.forestday.common.plugins.minetweaker.handler.MaceratorRecipeHandler;
import nedelosk.forestday.common.plugins.minetweaker.handler.WorkbenchRecipeHandler;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipeManager;
import nedelosk.nedeloskcore.plugins.Plugin;

public class PluginMineTweaker extends Plugin {

	@Override
	public void preInit() {
		MineTweakerAPI.registerClass(WorkbenchRecipeHandler.class);
		MineTweakerAPI.registerClass(MaceratorRecipeHandler.class);
		MineTweakerAPI.registerClass(AlloySmelterRecipeHandler.class);
	}
	
	@Override
	public String getRequiredMod() {
		return "MineTweaker3";
	}
	
}
