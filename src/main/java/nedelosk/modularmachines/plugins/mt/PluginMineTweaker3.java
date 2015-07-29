package nedelosk.modularmachines.plugins.mt;

import minetweaker.MineTweakerAPI;
import nedelosk.nedeloskcore.plugins.Plugin;

public class PluginMineTweaker3 extends Plugin {
	
	@Override
	public void preInit() {
		MineTweakerAPI.registerClass(ModularRecipeHandler.class);
	}
	
	@Override
	public String getRequiredMod() {
		return "MineTweaker3";
	}
	
}
