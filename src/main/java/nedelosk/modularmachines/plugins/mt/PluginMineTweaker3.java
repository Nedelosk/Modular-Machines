package nedelosk.modularmachines.plugins.mt;

import minetweaker.MineTweakerAPI;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.nedeloskcore.plugins.basic.Plugin;

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
