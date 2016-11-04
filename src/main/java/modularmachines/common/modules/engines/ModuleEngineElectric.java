package modularmachines.common.modules.engines;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.energy.IEnergyBuffer;
import modularmachines.api.modular.AssemblerException;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.IStorage;
import modularmachines.api.modules.storage.energy.IModuleBattery;
import modularmachines.common.utils.Translator;

public class ModuleEngineElectric extends ModuleEngine {

	public ModuleEngineElectric() {
		super("electric");
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IStorage storage, IModuleState state) throws AssemblerException {
		if (modular.getModules(IModuleBattery.class).isEmpty()) {
			throw new AssemblerException(Translator.translateToLocal("modular.assembler.error.no.battery"));
		}
	}

	@Override
	public List<IModuleState> getUsedModules(IModuleState state) {
		return new ArrayList(state.getModular().getModules(IModuleBattery.class));
	}

	@Override
	public boolean canWork(IModuleState state) {
		IModular modular = state.getModular();
		if (modular.getEnergyBuffer() == null) {
			return false;
		}
		if (modular.getEnergyBuffer().getEnergyStored() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeMaterial(IModuleState state) {
		IEnergyBuffer energyBuffer = state.getModular().getEnergyBuffer();
		if (energyBuffer == null) {
			return false;
		}
		if (energyBuffer.extractEnergy(state, null, getMaterialPerWork(state), true) == getMaterialPerWork(state)) {
			return energyBuffer.extractEnergy(state, null, getMaterialPerWork(state), false) == getMaterialPerWork(state);
		} else {
			return false;
		}
	}
}
