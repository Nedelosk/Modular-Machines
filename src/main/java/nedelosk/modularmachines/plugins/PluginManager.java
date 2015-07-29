package nedelosk.modularmachines.plugins;

import java.util.ArrayList;

import nedelosk.modularmachines.plugins.mt.PluginMineTweaker3;
import nedelosk.nedeloskcore.common.core.Log;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;

public class PluginManager extends nedelosk.nedeloskcore.plugins.PluginManager {
	
	public void registerPlugins()
	{
		registerPlugin(new PluginEnderIO());
		registerPlugin(new PluginMineTweaker3());
		registerPlugin(new PluginThermalExpansion());
	}
	
}
