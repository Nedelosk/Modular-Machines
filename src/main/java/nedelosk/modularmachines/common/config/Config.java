package nedelosk.modularmachines.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import com.enderio.core.common.event.ConfigFileChangedEvent;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import nedelosk.forestcore.library.Log;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

@Optional.Interface(modid = "endercore", iface = "com.enderio.core.common.event.ConfigFileChangedEvent")
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
	public static final ConfigGroup multiblocks = new ConfigGroup("Multiblocks", "multiblocks");
	public static final ConfigGroup moduleRegistry = new ConfigGroup("Modules.Registry", "moduleRegistry");
	public static final ConfigGroup moduleItems = new ConfigGroup("Modules.Items", "moduleItem");

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.modID.equals(Constants.MODID)) {
			Log.info("Updating config...");
			syncAllConfig(false);
		}
	}

	@SubscribeEvent
	@Optional.Method(modid = "endercore")
	public void onConfigFileChanged(ConfigFileChangedEvent event) {
		if (event.modID.equals(Constants.MODID)) {
			Log.info("Updating config...");
			syncAllConfig(true);
			event.setSuccessful();
		}
	}

	public static void syncAllConfig(boolean load) {
		try {
			if (load) {
				load();
			}
			processConfig();
			init();
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
		generateAluminiumOre = config.get(oreGen.name, "Aluminium", true).getBoolean();
		generateColumbiteOre = config.get(oreGen.name, "Columbite", true).getBoolean();
		moduleModular = config.get("Modules", "Modular", true, "Add a Modular System, Module and Producers").getBoolean();
		pluginTinkers = config.get(plugins.name, "Tinkers Construct", true).getBoolean();
		pluginEnderIO = config.get(plugins.name, "EnderIO", true).getBoolean();
		pluginThermalExpansion = config.get(plugins.name, "Thermal Expansion", true).getBoolean();
		pluginWaila = config.get(plugins.name, "Waila", true).getBoolean();
		pluginMineTweaker3 = config.get(plugins.name, "Mine Tweaker 3", true).getBoolean();
		bastFurnaceMaxHeat = config.get(multiblocks.name, "Blast Furnace", 1500).getInt();
		cokeOvenMaxHeat = config.get(multiblocks.name, "Coke Oven Plant", 1350).getInt();
		airHeatingPlantMaxHeat = config.get(multiblocks.name, "Air Heating Plant", 750).getInt();
	}

	public static void init() {
		for ( Entry<ResourceLocation, IModule> entry : ModuleRegistry.getModuleRegistry().getModules().entrySet() ) {
			if (!config.get(moduleRegistry.name, entry.getKey().toString(), true).getBoolean()) {
				ModuleRegistry.getModuleRegistry().getModules().remove(entry.getKey());
			}
		}
		/*
		 * ArrayList<ModuleStack> stacks =
		 * Lists.newArrayList(ModuleRegistry.getProducers().iterator()); for (
		 * ModuleStack module : stacks ) { String[] s =
		 * GameData.getItemRegistry().getNameForObject(module.getItem().getItem(
		 * )).split(":"); if (module.getItem() == null ||
		 * module.getItem().getItem() == null || !config .getBoolean(
		 * module.getModule().getName(module, false) + (module.getModule() !=
		 * null ? " : " + module.getModule().getName(module) : "") + " : " +
		 * module.getMaterial().getLocalName() + " : " +
		 * module.getItem().getUnlocalizedName(), "Modules." + s[0], true, ""))
		 * { ModuleRegistry.getProducers().remove(module);
		 * ItemProducers.getItems().remove(new Pair(module.getMaterial(),
		 * module.getModule())); } }
		 */
	}

	public static void save() {
		config.save();
	}

	public static int bastFurnaceMaxHeat;
	public static int cokeOvenMaxHeat;
	public static int airHeatingPlantMaxHeat;
	public static boolean generateColumbiteOre;
	public static boolean generateAluminiumOre;
	public static boolean moduleModular;
	public static boolean pluginTinkers;
	public static boolean pluginEnderIO;
	public static boolean pluginThermalExpansion;
	public static boolean pluginWaila;
	public static boolean pluginMineTweaker3;
}
