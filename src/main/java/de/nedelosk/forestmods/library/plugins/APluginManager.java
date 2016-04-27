package de.nedelosk.forestmods.library.plugins;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;
import de.nedelosk.forestmods.library.utils.Log;

public abstract class APluginManager {

	public ArrayList<IPlugin> plugins = new ArrayList<IPlugin>();

	public void registerPlugin(IPlugin plugin) {
		plugins.add(plugin);
		Log.logPluginManager(Level.INFO, "Register Plugin: " + plugin.getRequiredMod());
	}

	public void preInit() {
		for(IPlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption()) {
				plugin.preInit();
			}
		}
	}

	public void postInit() {
		for(IPlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption()) {
				plugin.postInit();
			}
		}
	}

	public void init() {
		for(IPlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption()) {
				plugin.init();
				plugin.registerRecipes();
			}
		}
	}

	public void registerPlugins() {
	}
}
