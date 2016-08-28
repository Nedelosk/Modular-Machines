package de.nedelosk.modularmachines.common.modules.generator;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public class ModuleKineticGenerator extends ModuleGenerator {

	public ModuleKineticGenerator() {
		super("kinetic");
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
		/*IEnergyBuffer energyBuffer = state.getModular().getEnergyBuffer();
		IHeatSource heatBuffer = state.getModular().getHeatSource();
		if(energyBuffer != null && heatBuffer != null && energyBuffer.getEnergyStored() > 0 && heatBuffer.getHeatStored() > 0){
			for(IModuleState<IModuleKinetic> otherState : modular.getModules(IModuleKinetic.class)){
				IKineticSource source = otherState.getModule().getKineticSource(otherState);
				if(source.getKineticEnergyStored() > 0){
					source.extractKineticEnergy(1, false);
					if(state.get(SPEED) < getMaxSpeed(state)){
						state.set(SPEED, state.get(SPEED)+0.01F);
					}else if(state.get(SPEED) > getMaxSpeed(state)){
						state.set(SPEED, getMaxSpeed(state));
					}
				}else{
					if(state.get(SPEED) > 0){
						state.set(SPEED, state.get(SPEED)-0.05F);
					}else if(0 > state.get(SPEED)){
						state.set(SPEED, 0F);
					}
				}
			}
			if(state.get(SPEED) > 0){
				workTime+=Math.round(state.get(SPEED));
			}
		}*/
	}
}
