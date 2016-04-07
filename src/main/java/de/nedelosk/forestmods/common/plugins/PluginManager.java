package de.nedelosk.forestmods.common.plugins;

import de.nedelosk.forestcore.plugins.APluginManager;
import de.nedelosk.forestmods.common.plugins.enderio.PluginEnderIO;
import de.nedelosk.forestmods.common.plugins.thermalexpansion.PluginThermalExpansion;
import de.nedelosk.forestmods.common.plugins.waila.PluginWaila;

public class PluginManager extends APluginManager {

	@Override
	public void registerPlugins() {
		registerPlugin(new PluginWaila());
		registerPlugin(new PluginEnderIO());
		registerPlugin(new PluginThermalExpansion());
	}
}
