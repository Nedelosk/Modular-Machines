package nedelosk.modularmachines.plugins;

import nedelosk.forestcore.library.plugins.APluginManager;
import nedelosk.modularmachines.plugins.mt.PluginMineTweaker3;
import nedelosk.modularmachines.plugins.tconstruct.PluginTConstruct;
import nedelosk.modularmachines.plugins.waila.PluginWaila;

public class PluginManager extends APluginManager {

	@Override
	public void registerPlugins() {
		registerPlugin(new PluginEnderIO());
		registerPlugin(new PluginMineTweaker3());
		registerPlugin(new PluginThermalExpansion());
		registerPlugin(new PluginTConstruct());
		registerPlugin(new PluginWaila());
	}

}
