package modularmachines.common.energy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeatManager {
	
	private static final List<HeatStep> HEAT_STEPS = new ArrayList<>();
	public static final float COLD_TEMP = 20;
	public static final int STEAM_PER_UNIT_WATER = 160;
	public static final float BOILING_POINT = 100;
	
	private HeatManager() {
	}
	
	static {
		registerType(COLD_TEMP, 0.1, 0.02);
		registerType(50, 0.085, 0.035);
		registerType(75, 0.080, 0.040);
		registerType(100, 0.075, 0.045);
		registerType(125, 0.070, 0.050);
		registerType(150, 0.065, 0.050);
		registerType(175, 0.045, 0.055);
		registerType(200, 0.035, 0.055);
		registerType(250, 0.030, 0.060);
		registerType(300, 0.025, 0.065);
		registerType(400, 0.020, 0.065);
		registerType(500, 0.015, 0.075);
		registerType(750, 0.005, 0.085);
	}
	
	public static void registerType(double heatMin, double heatStepUp, double heatStepDown) {
		HeatStep heatStep = new HeatStep(heatMin, heatStepUp, heatStepDown);
		if (!HEAT_STEPS.contains(heatStep)) {
			heatStep.setIndex(HEAT_STEPS.size());
			HEAT_STEPS.add(heatStep);
			Collections.sort(HEAT_STEPS);
		}
	}
	
	public static int getHeatLevelIndex(HeatStep level) {
		return HEAT_STEPS.indexOf(level);
	}
	
	public static HeatStep getHeatStep(double heat) {
		for (int i = HEAT_STEPS.size() - 1; i >= 0; i--) {
			HeatStep step = HEAT_STEPS.get(i);
			if (step.getHeatMin() <= heat) {
				return step;
			}
		}
		return HEAT_STEPS.get(0);
	}
}
