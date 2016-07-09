package de.nedelosk.modularmachines.common.modules.engine;

import cofh.api.energy.IEnergyProvider;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleMachine;

public class ModuleEngineSteam extends ModuleEngine {

	public ModuleEngineSteam(int burnTimeModifier, int materialPerTick) {
		super(burnTimeModifier, materialPerTick);
	}
	
	@Override
	public boolean removeMaterial(IModuleState state, IModuleState<IModuleMachine> machineState) {
		IEnergyProvider energyHandler = state.getModular().getEnergyHandler();
		IModuleTank
		if(energyHandler == null){
			return false;
		}
		if (energyHandler.extractEnergy(null, materialPerTick, true) == materialPerTick) {
			return energyHandler.extractEnergy(null, materialPerTick, false) == materialPerTick;
		} else {
			return false;
		}
	}

}