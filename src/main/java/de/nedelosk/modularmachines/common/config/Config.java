package de.nedelosk.modularmachines.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.nedelosk.modularmachines.api.modules.IModuleConfigurable;
import de.nedelosk.modularmachines.api.modules.IModulePropertiesConfigurable;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.common.core.Constants;
import de.nedelosk.modularmachines.common.utils.Log;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {

	public static final List<ConfigGroup> groups;
	public static Configuration config;

	/* MODULES */
	public static int defaultAllowedStorageComplexity = 4;
	public static int defaultAllowedCasingComplexity = 8;
	public static int defaultAllowedComplexity = 12;
	public static int defaultAllowedControllerComplexity = 16;

	/* PLUGINS */
	public static boolean pluginEnderIO;
	public static boolean pluginMekanism;
	public static boolean pluginThermalExpansion;
	public static boolean pluginTheOneProbe;

	/* ORES */
	public static boolean[] generateOre;

	static {
		groups = new ArrayList<>();
	}

	public Config() {
		FMLCommonHandler.instance().bus().register(this);
	}

	public static void load() {
		config.load();
	}


	public static void save() {
		if (config.hasChanged()) {
			config.save();
		}
	}

	public static class ConfigGroup {
		public final String name;
		public final String lang;
		public final boolean reloadMC;

		public ConfigGroup(String name, String lang, boolean reloadMC) {
			this.name = name;
			this.lang = lang;
			this.reloadMC = reloadMC;
			groups.add(this);
		}

		public ConfigGroup(String name, String lang) {
			this(name, lang, false);
		}

		public String getLowerCase() {
			return name.toLowerCase(Locale.ENGLISH);
		}
	}

	public static final ConfigGroup oreGen = new ConfigGroup("Ore Generation", "oreGen");
	public static final ConfigGroup plugins = new ConfigGroup("Plugins", "plugins", true);
	public static final ConfigGroup modules = new ConfigGroup("Modules", "modules", false);

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.getModID().equals(Constants.MODID)) {
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
			processModuleConfig();
		} catch (Exception e) {
			Log.err("Modular Machines has a problem loading it's configuration");
			e.printStackTrace();
		} finally {
			save();
		}
	}

	public static void processConfig() {
		//Modules
		defaultAllowedStorageComplexity = config.getInt("Default Allowed Storage Complexity", modules.name, 4, 1, 64, "");
		defaultAllowedCasingComplexity = config.getInt("Default Allowed Casing Complexity", modules.name, 8, 2, 64, "");
		defaultAllowedComplexity = config.getInt("Default Allowed Modular Machine Complexity", modules.name, 12, 4, 64, "");
		defaultAllowedControllerComplexity = config.getInt("Default Allowed Controller Complexity", modules.name, 16, 4, 256, "");

		//Plugins
		pluginEnderIO = config.get(plugins.name, "EnderIO", true).getBoolean();
		pluginMekanism = config.get(plugins.name, "Mekanism", true).getBoolean();
		pluginThermalExpansion = config.get(plugins.name, "Thermal Expansion", true).getBoolean();
		pluginTheOneProbe = config.get(plugins.name, "The One Probe", true).getBoolean();
		// Ores
		generateOre = config.get(oreGen.name, "Ore Generation", new boolean[] { true, true, true, true, true, true},
				"Ore Generation for Copper, Tin, Silver, Lead, Nickel, Aluminium").getBooleanList();
	}

	public static void processModuleConfig(){
		for(IModuleItemContainer itemContainer : ModuleManager.MODULE_CONTAINERS){
			if(itemContainer != null){
				for(IModuleContainer container : itemContainer.getContainers()){
					if(container.getModule() instanceof IModuleConfigurable){
						((IModuleConfigurable)container.getModule()).processConfig(container, config);
					}
					if(container.getProperties() instanceof IModulePropertiesConfigurable){
						((IModulePropertiesConfigurable)container.getProperties()).processConfig(container, config);
					}
				}
			}
		}
	}
}
