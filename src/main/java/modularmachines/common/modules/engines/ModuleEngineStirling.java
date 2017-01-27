package modularmachines.common.modules.engines;

public class ModuleEngineStirling extends ModuleEngine {

	public ModuleEngineStirling() {
		super("stirling");
	}

	@Override
	public boolean canWork(IModuleState state) {
		IModular modular = state.getModular();
		if (modular.getEnergyBuffer() == null) {
			return false;
		}
		return modular.getEnergyBuffer().getEnergyStored() > 0;
	}

	@Override
	public boolean removeMaterial(IModuleState state) {
		IHeatSource heatBuffer = state.getModular().getHeatSource();
		if (heatBuffer == null) {
			return false;
		}
		if (heatBuffer.extractHeat(getMaterialPerWork(state), true) == getMaterialPerWork(state)) {
			return heatBuffer.extractHeat(getMaterialPerWork(state), false) == getMaterialPerWork(state);
		} else {
			return false;
		}
	}
}
