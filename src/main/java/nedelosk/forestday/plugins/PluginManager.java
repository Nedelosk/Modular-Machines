package nedelosk.forestday.plugins;

import nedelosk.forestday.common.plugins.minetweaker.PluginMineTweaker;
import nedelosk.forestday.common.plugins.waila.PluginWaila;

public class PluginManager extends nedelosk.forestday.plugins.basic.PluginManager {

	@Override
	public void preInit() {
		registerPlugin(new PluginWaila());
		registerPlugin(new PluginMineTweaker());

		super.preInit();
	}

}
