package de.nedelosk.modularmachines.api.energy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeatManager {

	private static final List<HeatLevel> HEAT_LEVELS = new ArrayList<>();
	public static final float COLD_TEMP = 20;
	public static final int STEAM_PER_UNIT_WATER = 160;
	public static final float BOILING_POINT = 100;

	private HeatManager() {
	}

	static {
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

	public static void registerType(HeatLevel heatLevel) {
		if (!HEAT_LEVELS.contains(heatLevel)) {
			HEAT_LEVELS.add(heatLevel);
			Collections.sort(HEAT_LEVELS);
		}
	}

	public static int getHeatLevelIndex(HeatLevel level) {
		return HEAT_LEVELS.indexOf(level);
	}

	public static HeatLevel getHeatLevel(double heat) {
		for(int i = HEAT_LEVELS.size() - 1; i >= 0; i--) {
			HeatLevel level = HEAT_LEVELS.get(i);
			if (level.getHeatMin() <= heat) {
				return level;
			}
		}
		return null;
	}
}
