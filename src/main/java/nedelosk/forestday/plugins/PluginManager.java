package nedelosk.forestday.plugins;

import nedelosk.forestcore.library.plugins.APluginManager;
import nedelosk.forestday.plugins.minetweaker.PluginMineTweaker;
import nedelosk.forestday.plugins.waila.PluginWaila;

public class PluginManager extends APluginManager {

	@Override
	public void registerPlugins() {
		registerPlugin(new PluginWaila());
		registerPlugin(new PluginMineTweaker());
	}
}
