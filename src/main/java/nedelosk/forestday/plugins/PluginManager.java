package nedelosk.forestday.plugins;

import nedelosk.forestcore.api.plugins.APluginManager;
import nedelosk.forestday.common.plugins.minetweaker.PluginMineTweaker;
import nedelosk.forestday.common.plugins.waila.PluginWaila;

public class PluginManager extends APluginManager {
	
	@Override
	public void registerPlugins() {
		registerPlugin(new PluginWaila());
		registerPlugin(new PluginMineTweaker());
	}

}
