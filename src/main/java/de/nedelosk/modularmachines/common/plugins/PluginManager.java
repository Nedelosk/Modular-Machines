package de.nedelosk.modularmachines.common.plugins;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import de.nedelosk.modularmachines.common.plugins.enderio.PluginEnderIO;
import de.nedelosk.modularmachines.common.plugins.waila.PluginWaila;
import de.nedelosk.modularmachines.common.utils.Log;
import net.minecraftforge.fml.common.Loader;

public class PluginManager {

	public ArrayList<APlugin> plugins = new ArrayList<APlugin>();

	public void registerPlugin(APlugin plugin) {
		plugins.add(plugin);
		Log.logPluginManager(Level.INFO, "Register Plugin: " + plugin.getRequiredMod());
	}

	public void preInit() {
		for(APlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption()) {
				plugin.preInit();
			}
		}
	}

	public void postInit() {
		for(APlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption()) {
				plugin.postInit();
			}
		}
	}

	public void init() {
		for(APlugin plugin : plugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption()) {
				plugin.init();
				plugin.registerRecipes();
			}
		}
	}

	public void registerPlugins() {
		registerPlugin(new PluginWaila());
		registerPlugin(new PluginEnderIO());
		//registerPlugin(new PluginThermalExpansion());
	}
}
