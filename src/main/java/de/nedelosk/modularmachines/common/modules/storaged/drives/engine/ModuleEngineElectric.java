package de.nedelosk.modularmachines.common.modules.storaged.drives.engine;

import java.util.Locale;

import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.IModuleIndexStorage;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.storage.IModuleBattery;
import net.minecraftforge.items.IItemHandler;

public class ModuleEngineElectric extends ModuleEngine {

	protected IEnergyType type;

	public ModuleEngineElectric(int complexity, int kineticModifier, int maxKineticEnergy, int energyPerWork, IEnergyType type) {
		super("engine.electric." + type.getShortName().toLowerCase(Locale.ENGLISH), complexity, kineticModifier, maxKineticEnergy, energyPerWork);
		this.type = type;
	}

	@Override
	public String getDescription(IModuleContainer container) {
		return super.getDescription(container);
	}

	@Override
	public boolean assembleModule(IModularAssembler assembler, IModular modular, IModuleState state, IModuleIndexStorage storage) {
		if(!super.assembleModule(assembler, modular, state, storage)){
			return false;
		}
		if(modular.getModules(IModuleBattery.class).isEmpty()){
			return false;
		}
		return true;
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
		if (energyHandler.extractEnergy(type, materialPerWork, true) == materialPerWork) {
			return energyHandler.extractEnergy(type, materialPerWork, false) == materialPerWork;
		} else {
			return false;
		}
	}
}
