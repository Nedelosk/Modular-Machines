package de.nedelosk.forestmods.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.enderio.core.common.event.ConfigFileChangedEvent;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.nedelosk.forestcore.utils.Log;
import de.nedelosk.forestmods.common.core.Constants;
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
	public static final ConfigGroup machineCharcoalKiln = new ConfigGroup("Machines.Kiln", "machineKilnCharcoal");
	public static final ConfigGroup machineWorktable = new ConfigGroup("Machines.Worktable", "machineWorktable");
	public static final ConfigGroup machineCampfire = new ConfigGroup("Machines.Campfire", "machineCampfire");
	public static final ConfigGroup modModules = new ConfigGroup("ModModules", "modModules", true);

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
			postInit();
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
		isForestDayActive = config.get(modModules.name, "Forest Day", true).getBoolean();
		isModularMachinesActive = config.get(modModules.name, "Modular Machines", true).getBoolean();
		pluginTinkers = config.get(plugins.name, "Tinkers Construct", true).getBoolean();
		pluginEnderIO = config.get(plugins.name, "EnderIO", true).getBoolean();
		pluginThermalExpansion = config.get(plugins.name, "Thermal Expansion", true).getBoolean();
		pluginWaila = config.get(plugins.name, "Waila", true).getBoolean();
		pluginMineTweaker3 = config.get(plugins.name, "Mine Tweaker 3", true).getBoolean();
		bastFurnaceMaxHeat = config.get(multiblocks.name, "Blast Furnace", 1500).getInt();
		cokeOvenMaxHeat = config.get(multiblocks.name, "Coke Oven Plant", 1350).getInt();
		airHeatingPlantMaxHeat = config.get(multiblocks.name, "Air Heating Plant", 750).getInt();
		kilnBurnTime = config.get(machineCharcoalKiln.name, "BurnTime", 700).getInt();
		kilnMinHeat = config.get(machineCharcoalKiln.name, "MinHeat", 125).getInt();
		kilnMaxHeat = config.get(machineCharcoalKiln.name, "MaxHeat", 750).getInt();
		// Worktable
		worktableBurnTime = config.get(machineWorktable.name, "Burn Time", 70).getInt();
		// Campfire
		campfireFuelStorageMax = config.get(machineCampfire.name, "Fuel Storage Max", new int[] { 2500, 5000 }).getIntList();
		// Campfire Items
		campfireCurbs = config.get(machineCampfire.name, "Curbs", campfireCurbsDefault).getStringList();
		campfirePots = config.get(machineCampfire.name, "Ports", campfirePotsDefault).getStringList();
		campfirePotHolders = config.get(machineCampfire.name, "Pot Holders", campfirePotHoldersDefault).getStringList();
		campfirePotCapacity = config.get(machineCampfire.name, "Pot Capacity", campfirePotCapacityDefault).getIntList();
		// Charcoal Kiln
		charcoalKilnBurnTime = config.get(machineCharcoalKiln.name, "BurnTime", 12000).getInt();
		// Ores
		generateOre = config.get(oreGen.name, "Ore Generation", new boolean[] { true, true, true, true, true, true, true },
				"Ore Generation for Copper, Tin, Silver, Lead, Nickel, Aluminium, Columbite.").getBooleanList();
	}

	public static void postInit() {
		/*
		 * for ( Entry<ModuleUID, IModule> entry :
		 * ModuleManager.moduleRegistry.getModuleMaps().getModules().entrySet()
		 * ) { if (!config.get(moduleRegistry.name, entry.getKey().toString(),
		 * true).getBoolean()) {
		 * ModuleRegistry.getModuleMaps().getModules().remove(entry.getKey()); }
		 * }
		 */
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

	/* MODULES */
	public static boolean isModularMachinesActive;
	public static boolean isForestDayActive;
	/* MODULAR MACHINES */
	public static int bastFurnaceMaxHeat;
	public static int cokeOvenMaxHeat;
	public static int airHeatingPlantMaxHeat;
	public static boolean pluginTinkers;
	public static boolean pluginEnderIO;
	public static boolean pluginThermalExpansion;
	public static boolean pluginWaila;
	public static boolean pluginMineTweaker3;
	/* FOREST DAY */
	public static int kilnMinHeat;
	public static int kilnMaxHeat;
	public static int kilnBurnTime;
	public static int worktableBurnTime;
	public static int[] campfireFuelStorageMax;
	public static String[] campfireCurbsDefault = new String[] { "stone", "obsidian" };
	public static String[] campfirePotsDefault = new String[] { "stone", "bronze", "iron", "steel" };
	public static int[] campfirePotCapacityDefault = new int[] { 750, 1250, 2500, 5000 };
	public static String[] campfirePotHoldersDefault = new String[] { "wood", "stone", "bronze", "iron" };
	public static String[] campfireCurbs;
	public static String[] campfirePots;
	public static int[] campfirePotCapacity;
	public static String[] campfirePotHolders;
	public static int charcoalKilnBurnTime;
	public static boolean[] generateOre;
}
