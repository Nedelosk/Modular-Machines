package nedelosk.modularmachines.plugins;

import java.util.ArrayList;

import nedelosk.nedeloskcore.common.core.Log;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;

public class PluginManager extends nedelosk.nedeloskcore.plugins.PluginManager {
	
	public static void registerPlugins()
	{
		registerPlugin(new PluginEnderIO());
	}
	
}
