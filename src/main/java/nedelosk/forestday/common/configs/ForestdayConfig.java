package nedelosk.forestday.common.configs;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ForestDayConfig {

	public static void loadConfig(File file) {
		Configuration config = new Configuration(file);

		config.load();

		// Resin Kiln
		kilnBurnTime = config.get("Kiln", "BurnTime", 700).getInt();
		kilnMinHeat = config.get("Kiln", "MinHeat", 125).getInt();
		kilnMaxHeat = config.get("Kiln", "MaxHeat", 750).getInt();

		// Worktable
		worktableBurnTime = config.get("Worktable", "Burn Time", 70).getInt();

		// Campfire
		campfireFuelStorageMax = config.get("Campfire", "Fuel Storage Max", new int[] { 2500, 5000 }).getIntList();

		// Campfire Items
		campfireCurbs = config.get("Campfire Items", "Curbs", campfireCurbsDefault).getStringList();
		campfirePots = config.get("Campfire Items", "Ports", campfirePotsDefault).getStringList();
		campfirePotHolders = config.get("Campfire Items", "Pot Holders", campfirePotHoldersDefault).getStringList();
		campfirePotCapacity = config.get("Campfire Items", "Pot Capacity", campfirePotCapacityDefault).getIntList();

		// Charcoal Kiln
		charcoalKilnBurnTime = config.get("Charcoal Kiln", "BurnTime", 12000).getInt();

		// Ores
		generateOre = config.get("World Generation", "Ore Generation", new boolean[] { true, true, true, true, true },
				"Ore Generation for Copper, Tin, Silver, Lead, Nickel.").getBooleanList();

		config.save();

	}

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
