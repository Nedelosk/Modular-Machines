package modularmachines.common.modules.engines;

import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.common.utils.ModuleUtil;

public class ModuleEngineStirling extends ModuleEngine {
	
	public ModuleEngineStirling(int capacity, int maxTransfer, int materialPerWork, double kineticModifier) {
		super(capacity, maxTransfer, materialPerWork, kineticModifier);
	}
	
	@Override
	public boolean canWork() {
		IHeatSource heatSource = ModuleUtil.getHeat(container);
		if (heatSource == null) {
			return false;
		}
		return heatSource.getHeatStored() > 0;
	}
	
	@Override
	public boolean removeMaterial() {
		IHeatSource heatSource = ModuleUtil.getHeat(container);
		if (heatSource == null) {
			return false;
		}
		if (heatSource.extractHeat(getMaterialPerWork(), true) == getMaterialPerWork()) {
			return heatSource.extractHeat(getMaterialPerWork(), false) == getMaterialPerWork();
		} else {
			return false;
		}
	}
}
