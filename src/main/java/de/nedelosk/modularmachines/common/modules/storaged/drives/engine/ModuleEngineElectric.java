package de.nedelosk.modularmachines.common.modules.storaged.drives.engine;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.utils.Translator;

public class ModuleEngineElectric extends ModuleEngine {

	public ModuleEngineElectric() {
		super("engine.electric");
	}

	@Override
	public String getDescription(IModuleContainer container) {
		return super.getDescription(container);
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IModuleStorage storage, IModuleState state) throws AssemblerException {
		if(modular.getModules(IModuleBattery.class).isEmpty()){
			throw new AssemblerException(Translator.translateToLocal("modular.assembler.error.no.battery"));
		}
	}

	@Override
	public boolean canWork(IModuleState state) {
		IModular modular = state.getModular();
		if(modular.getEnergyBuffer() == null){
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
		if(energyBuffer == null){
			return false;
		}
		if (energyBuffer.extractEnergy(null, getMaterialPerWork(state), true) == getMaterialPerWork(state)) {
			return energyBuffer.extractEnergy(null, getMaterialPerWork(state), false) == getMaterialPerWork(state);
		} else {
			return false;
		}
	}
}
