package nedelosk.nedeloskcore.plugins;

import nedelosk.nedeloskcore.plugins.basic.PluginManager;

public class NCPluginManager extends PluginManager {
	
	public void registerPlugins()
	{
		registerPlugin(new PluginRailcraft());
	}
	
}
