package de.nedelosk.modularmachines.common.modules.engine;

import cofh.api.energy.IEnergyProvider;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModuleIndexStorage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.api.modules.tool.IModuleMachine;
import net.minecraftforge.items.IItemHandler;

public class ModuleEngineEnergy extends ModuleEngine {

	public ModuleEngineEnergy(int burnTimeModifier, int materialPerTick) {
		super(burnTimeModifier, materialPerTick);
	}

	@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state, IModuleIndexStorage storage) {
		if(!super.assembleModule(itemHandler, modular, state, storage)){
			return false;
		}
		if(modular.getModules(IModuleBattery.class).isEmpty()){
			return false;
		}
		return true;
	}

	@Override
	public boolean removeMaterial(IModuleState state, IModuleState<IModuleMachine> machineState) {
		IEnergyProvider energyHandler = state.getModular().getEnergyHandler();
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
