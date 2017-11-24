package modularmachines.common.config;

import net.minecraftforge.common.config.Configuration;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import modularmachines.common.core.Constants;
import modularmachines.common.utils.Log;

public class Config {
	
	public static Configuration config;
	public static int defaultAllowedComplexity;
	public static boolean destroyModules;
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Constants.MOD_ID)) {
			Log.info("Updating config...");
			syncConfig(false);
		}
	}
	
	public static void syncConfig(boolean load) {
		try {
			if (load) {
				load();
			}
			processConfig();
		} catch (Exception e) {
			Log.err("Modular Machines has a problem loading it's configuration");
			e.printStackTrace();
		} finally {
			save();
		}
	}
	
	public static void load() {
		config.load();
	}
	
	public static void save() {
		if (config.hasChanged()) {
			config.save();
		}
	}
	
	public static void processConfig() {
	
	}
	
}
