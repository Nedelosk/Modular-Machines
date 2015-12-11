package nedelosk.forestcore.api.plugins;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;
import nedelosk.forestcore.api.Log;

public abstract class APluginManager {

	public ArrayList<IPlugin> plugins = new ArrayList<IPlugin>();
	
	public void registerPlugin(IPlugin plugin) {
		plugins.add(plugin);
		Log.logPluginManager(Level.INFO, "Register Plugin: " + plugin.getRequiredMod());
	}

	public void preInitPlugins() {
		for (IPlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null)
					&& plugin.getConfigOption()) {
				plugin.preInit();
			}
		}
	}

	public void postInitPlugins() {
		for (IPlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null)
					&& plugin.getConfigOption()) {
				plugin.postInit();
			}
		}
	}

	public void initPlugins() {
		for (IPlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null)
					&& plugin.getConfigOption()) {
				plugin.init();
				plugin.registerRecipes();
			}
		}
	}
	
	public void registerPlugins() {
		
	}

}
