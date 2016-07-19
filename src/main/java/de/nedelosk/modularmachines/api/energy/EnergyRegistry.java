package de.nedelosk.modularmachines.api.energy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class EnergyRegistry {

	private static final Map<Pair<IEnergyType, IEnergyType>, IEnergyTransformer> transformers = new HashMap<>();
	private static final List<IEnergyType> types = new ArrayList<>();
	private static final List<IHeatLevel> heatLevels = new ArrayList<>();

	public static final float COLD_TEMP = 20;
	public static final int STEAM_PER_UNIT_WATER = 160;
	public static final float BOILING_POINT = 100;

	public static IEnergyType redstoneFlux;
	public static IEnergyType energyUnit;

	static{
		registerType(new HeatLevel(COLD_TEMP, 0.1, 0.02));
		registerType(new HeatLevel(50, 0.085, 0.035));
		registerType(new HeatLevel(100, 0.075, 0.045));
		registerType(new HeatLevel(150, 0.065, 0.050));
		registerType(new HeatLevel(200, 0.035, 0.055));
		registerType(new HeatLevel(250, 0.030, 0.060));
		registerType(new HeatLevel(300, 0.025, 0.065));
		registerType(new HeatLevel(400, 0.020, 0.065));
		registerType(new HeatLevel(500, 0.015, 0.075));
		registerType(new HeatLevel(750, 0.005, 0.085));
	}

	public static void registerType(IHeatLevel heatLevel){
		if(!heatLevels.contains(heatLevel)){
			heatLevels.add(heatLevel);
			Collections.sort(heatLevels);
		}
	}

	public static int getHeatLevelIndex(IHeatLevel level){
		return heatLevels.indexOf(level);

	}

	public static IHeatLevel getHeatLevel(double heat){
		for(int i = heatLevels.size() - 1;i >= 0;i--){
			IHeatLevel level = heatLevels.get(i);
			if(level.getHeatMin() <= heat){
				return level;
			}
		}
		return null;
	}

	public static void registerType(IEnergyType type){
		if(!types.contains(type)){
			types.add(type);
		}
	}

	public static void registerTransformer(IEnergyTransformer transformer){
		IEnergyType input = transformer.getInputType();
		IEnergyType output = transformer.getOutputType();
		Pair<IEnergyType, IEnergyType> pair = Pair.of(input, output);
		if(!transformers.containsKey(pair)){
			transformers.put(pair, transformer);
		}
	}
}
