package nedelosk.modularmachines.plugins;

import nedelosk.modularmachines.plugins.mt.PluginMineTweaker3;
import nedelosk.modularmachines.plugins.tconstruct.PluginTConstruct;
import nedelosk.modularmachines.plugins.waila.PluginWaila;

public class PluginManager extends nedelosk.forestday.plugins.basic.PluginManager {

	public void registerPlugins() {
		registerPlugin(new PluginEnderIO());
		registerPlugin(new PluginMineTweaker3());
		registerPlugin(new PluginThermalExpansion());
		registerPlugin(new PluginTConstruct());
		registerPlugin(new PluginWaila());
	}

}
