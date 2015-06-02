package nedelosk.forestday.common.config;

import java.io.File;

import nedelosk.forestday.structure.base.blocks.BlockCoilGrinding;
import net.minecraftforge.common.config.Configuration;

public class ForestdayConfig {
	
	public static void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		
		config.load();
		activeCreativeDrop = config.get("Creative", "Creative Drops", false, "get drops in the Creative Mode from Forestday Blocks").getBoolean(false);
		
		//Blast Furnace
		blastFurnaceGasInput = config.getInt("Input Gas", "Blast Furnace", 100, 20, 1000, "The input of the Air Hot of the Blast Furnace");
		blastFurnaceGasOutput = config.getInt("Output Gas", "Blast Furnace", 100, 20, 1000, "The output of the Blast Furnace Gas of the Blast Furnace");
		
		//Air Heater
		airHeaterGasInput = config.getInt("Input Gas", "Air Heater", 10, 1, 100, "The input of the Blast Furnace Gas of the Blast Furnace");
		airHeaterGasOutput = config.getInt("Output Gas", "Air Heater", 20, 1, 100, "The output of the Air Hot of the Blast Furnace");
		
		//Kiln
		kilnBurnTime = config.get("Kiln", "BurnTime", 700).getInt();
		kilnMinHeat = config.get("Kiln", "MinHeat", 125).getInt();
		
		//Macerator
		coilGrinding = config.get("Coil", "Coil Grinding Sharpness", BlockCoilGrinding.coilMaxSharpness).getIntList();
		
		//Fan
		fanAirProduction = config.get("Fan", "Air Production", 10).getInt();
		
		//Saw
		sawBurnTime = config.get("Saw", "Burn Time", 90).getInt();
		
		//Heat Generator
		generatorHeatBurnTime = config.get("Heat Generator", "Burn Time", 25).getInt();
		
		//Fluid Heater
		fluidheaterMinHeat = config.get("Fluid Heater", "MinHeat", 175).getInt();
		
		//Worktable
		worktableBurnTime = config.get("Worktable", "Burn Time", 70).getInt();
		
		//Worldgen
		generateCopper = config.get("worldgen", "Cooper", true).getBoolean(true);
		generateTin = config.get("worldgen", "Tin", true).getBoolean(true);
		generateLimestone = config.get("worldgen", "Limestone", true).getBoolean(true);
		
		config.save();
		
	}
	
	public static boolean activeCreativeDrop;
	
	public static int blastFurnaceGasOutput;
	public static int blastFurnaceGasInput;
	
	public static int airHeaterGasOutput;
	public static int airHeaterGasInput;
	
	public static int kilnMinHeat;
	public static int kilnBurnTime;
	
	public static int fluidheaterMinHeat;
	
	public static int generatorHeatBurnTime;
	
	public static int sawBurnTime;
	
	public static int worktableBurnTime;
	
	public static int fanAirProduction;
	
	public static int[] coilGrinding;
	
	public static boolean generateCopper;
	public static boolean generateTin;
	public static boolean generateLimestone;

}
