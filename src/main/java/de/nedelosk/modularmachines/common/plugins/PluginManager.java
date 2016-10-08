package de.nedelosk.modularmachines.common.plugins;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import de.nedelosk.modularmachines.common.plugins.enderio.PluginEnderIO;
import de.nedelosk.modularmachines.common.plugins.forestry.PluginForestry;
import de.nedelosk.modularmachines.common.plugins.ic2.PluginIC2;
import de.nedelosk.modularmachines.common.plugins.mekanism.PluginMekanism;
import de.nedelosk.modularmachines.common.plugins.theoneprobe.PluginTheOneProbe;
import de.nedelosk.modularmachines.common.utils.Log;
import net.minecraftforge.fml.common.Loader;

public class PluginManager {

	public final ArrayList<APlugin> loadedPlugins = new ArrayList<>();
	public final ArrayList<APlugin> registeredPlugins = new ArrayList<>();

	private void registerPlugin(APlugin plugin) {
		registeredPlugins.add(plugin);
		Log.logPluginManager(Level.INFO, "Register Plugin: " + plugin.getRequiredMod());
	}

	private void loadPlugins(){
		for(APlugin plugin : registeredPlugins) {
			if ((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.isActive()) {
				loadedPlugins.add(plugin);
			}
			Log.logPluginManager(Level.INFO, "Load Plugin: " + plugin.getRequiredMod());
		}
	}

	public void preInit() {
		registerPlugin(new PluginIC2());
		registerPlugin(new PluginTheOneProbe());
		registerPlugin(new PluginEnderIO());
		registerPlugin(new PluginMekanism());
		registerPlugin(new PluginForestry());
		loadPlugins();
		
		for(APlugin plugin : loadedPlugins) {
			plugin.preInit();
		}
	}

	public void postInit() {
		for(APlugin plugin : loadedPlugins) {
			plugin.postInit();
		}
	}

	public void init() {
		for(APlugin plugin : loadedPlugins) {
			plugin.init();
			plugin.registerRecipes();
		}
	}
}
