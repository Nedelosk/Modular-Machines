package nedelosk.forestday.common.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ForestdayConfig {
	
	public static void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		
		config.load();
		activeCreativeDrop = config.get("Creative", "Creative Drops", false, "get drops in the Creative Mode from Forestday Blocks").getBoolean(false);
		
		//Kiln
		kilnBurnTime = config.get("Kiln", "BurnTime", 700).getInt();
		kilnMinHeat = config.get("Kiln", "MinHeat", 125).getInt();
			
		//Fan
		fanAirProduction = config.get("Fan", "Air Production", 10).getInt();
		
		//Saw
		sawBurnTime = config.get("Saw", "Burn Time", 90).getInt();
		
		//Heat Generator
		generatorHeatBurnTime = config.get("Heat Generator", "Burn Time", 250).getInt();
		
		//Fluid Heater
		fluidheaterMinHeat = config.get("Fluid Heater", "MinHeat", 175).getInt();
		
		//Worktable
		worktableBurnTime = config.get("Worktable", "Burn Time", 70).getInt();
		
		//Worldgen
		generateCopper = config.get("worldgen", "Cooper", true).getBoolean(true);
		generateTin = config.get("worldgen", "Tin", true).getBoolean(true);
		generateLimestone = config.get("worldgen", "Limestone", true).getBoolean(true);
		
		campfireFuelStorageMax = config.get("Campfire", "Fuel Storage Max", new int[]{ 2500, 5000, 7500 , 10000, 15000}).getIntList();
		
		campfireCurbs = config.get("Campfire Items", "Curbs", campfireCurbsDefault).getStringList();
		campfirePots = config.get("Campfire Items", "Ports", campfirePotsDefault).getStringList();
		campfirePotHolders = config.get("Campfire Items", "Pot Holders", campfirePotHoldersDefault).getStringList();
		campfirePotCapacity = config.get("Campfire Items", "Pot Capacity", campfirePotCapacityDefault).getIntList();
		
		bowandstickPowerMin = config.get("Bow and Stick", "MinPower", new int[]{ 125, 75, 50}).getIntList();
		
		charcoalKilnBurnTime = config.get("Charcoal Kiln", "BurnTime", 9600).getInt();
		
		config.save();
		
	}
	
	public static boolean activeCreativeDrop;
	
	public static int kilnMinHeat;
	public static int kilnBurnTime;
	
	public static int fluidheaterMinHeat;
	
	public static int generatorHeatBurnTime;
	
	public static int sawBurnTime;
	
	public static int worktableBurnTime;
	
	public static int fanAirProduction;
	
	public static boolean generateCopper;
	public static boolean generateTin;
	public static boolean generateLimestone;

	public static int[] campfireFuelStorageMax;
	
	public static String[] campfireCurbsDefault = new String[] { "stone", "obsidian" };
	public static String[] campfirePotsDefault = new String[] { "stone", "bronze", "iron", "steel" };
	public static int[] campfirePotCapacityDefault = new int[] { 750, 1250, 2500, 5000 };
	public static String[] campfirePotHoldersDefault = new String[] { "wood", "stone", "bronze", "iron" };
	
	public static String[] campfireCurbs;
	public static String[] campfirePots;
	public static int[] campfirePotCapacity;
	public static String[] campfirePotHolders;

	public static int[] bowandstickPowerMin;
	
	public static int charcoalKilnBurnTime;

}
