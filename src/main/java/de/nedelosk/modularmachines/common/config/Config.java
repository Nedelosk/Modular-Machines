package de.nedelosk.modularmachines.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.nedelosk.modularmachines.common.core.Constants;
import de.nedelosk.modularmachines.common.utils.Log;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {

	public static Configuration config;

	public Config() {
		FMLCommonHandler.instance().bus().register(this);
	}

	public static void load() {
		config.load();
	}

	public static class ConfigGroup {

		public final String name;
		public final String lang;
		public final boolean reloadMC;

		public ConfigGroup(String name, String lang, boolean reloadMC) {
			this.name = name;
			this.lang = lang;
			this.reloadMC = reloadMC;
			register();
		}

		public ConfigGroup(String name, String lang) {
			this.name = name;
			this.lang = lang;
			this.reloadMC = false;
			register();
		}

		private void register() {
			groups.add(this);
		}

		public String lc() {
			return name.toLowerCase(Locale.US);
		}
	}

	public static final List<ConfigGroup> groups;

	static {
		groups = new ArrayList<ConfigGroup>();
	}

	public static final ConfigGroup oreGen = new ConfigGroup("Ore Generation", "oreGen");
	public static final ConfigGroup plugins = new ConfigGroup("Plugins", "plugins", true);

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.getModID().equals(Constants.MODID)) {
			Log.info("Updating config...");
			syncAllConfig(false);
		}
	}

	public static void syncAllConfig(boolean load) {
		try {
			if (load) {
				load();
			}
			processConfig();
		} catch (Exception e) {
			Log.err("Modular Machines has a problem loading it's configuration");
			e.printStackTrace();
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
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
			if (config.hasChanged()) {
				config.save();
			}
		}
	}

	public static void processConfig() {
		pluginEnderIO = config.get(plugins.name, "EnderIO", true).getBoolean();
		pluginThermalExpansion = config.get(plugins.name, "Thermal Expansion", true).getBoolean();
		pluginWaila = config.get(plugins.name, "Waila", true).getBoolean();
		// Ores
		generateOre = config.get(oreGen.name, "Ore Generation", new boolean[] { true, true, true, true, true, true},
				"Ore Generation for Copper, Tin, Silver, Lead, Nickel, Aluminium").getBooleanList();
	}

	public static void save() {
		config.save();
	}

	/* MODULAR MACHINES */
	public static boolean pluginEnderIO;
	public static boolean pluginThermalExpansion;
	public static boolean pluginWaila;
	/* FOREST DAY */
	public static boolean[] generateOre;
}
