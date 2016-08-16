package de.nedelosk.modularmachines.common.modules.storaged.drives.engine;

import java.util.Locale;

import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.utils.Translator;

public class ModuleEngineElectric extends ModuleEngine {

	protected IEnergyType type;

	public ModuleEngineElectric(IEnergyType type) {
		super("engine.electric." + type.getShortName().toLowerCase(Locale.ENGLISH));
		this.type = type;
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
		if(modular.getEnergyInterface() == null){
			return false;
		}
		if (modular.getEnergyInterface().getEnergyStored(type) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeMaterial(IModuleState state) {
		IEnergyInterface energyHandler = state.getModular().getEnergyInterface();
		if(energyHandler == null){
			return false;
		}
		if (energyHandler.extractEnergy(type, getMaterialPerWork(state), true) == getMaterialPerWork(state)) {
			return energyHandler.extractEnergy(type, getMaterialPerWork(state), false) == getMaterialPerWork(state);
		} else {
			return false;
		}
	}
}
