package nedelosk.forestday.common.plugins;

import nedelosk.forestday.common.plugins.minetweaker.PluginMineTweaker;
import nedelosk.forestday.common.plugins.waila.PluginWaila;

public class PluginManager extends nedelosk.nedeloskcore.plugins.basic.PluginManager {
	
	@Override
	public void preInit() {
		registerPlugin(new PluginWaila());
		registerPlugin(new PluginMineTweaker());
		
		super.preInit();
	}
	
}
