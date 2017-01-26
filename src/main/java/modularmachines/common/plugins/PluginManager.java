package modularmachines.common.plugins;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import modularmachines.common.utils.Log;

public class PluginManager {

	public final List<Plugin> loadedPlugins = new ArrayList<>();
	public final List<Plugin> registeredPlugins = new ArrayList<>();

	private void registerPlugin(Plugin plugin) {
		registeredPlugins.add(plugin);
		Log.logPlugin(Level.INFO, "Register Plugin: " + plugin.getMod());
	}

	private void loadPlugins() {
		registerPlugins();
		for (Plugin plugin : registeredPlugins) {
			if ((plugin.getMod() == null || plugin.getMod().active()) && plugin.isActive()) {
				loadedPlugins.add(plugin);
			}
			Log.logPlugin(Level.INFO, "Load Plugin: " + plugin.getMod());
		}
	}
	
	public void registerPlugins(){
		
	}

	public void preInit() {
		loadPlugins();
		for (Plugin plugin : loadedPlugins) {
			try {
				plugin.preInit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void init() {
		for (Plugin plugin : loadedPlugins) {
			try {
				plugin.init();
				plugin.registerRecipes();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void postInit() {
		for (Plugin plugin : loadedPlugins) {
			try {
				plugin.postInit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
