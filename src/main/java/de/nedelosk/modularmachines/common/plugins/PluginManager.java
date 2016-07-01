package de.nedelosk.modularmachines.common.plugins;

import de.nedelosk.modularmachines.common.plugins.enderio.PluginEnderIO;
import de.nedelosk.modularmachines.common.plugins.waila.PluginWaila;

public class PluginManager extends APluginManager {

	@Override
	public void registerPlugins() {
		registerPlugin(new PluginWaila());
		registerPlugin(new PluginEnderIO());
		//registerPlugin(new PluginThermalExpansion());
	}
}
