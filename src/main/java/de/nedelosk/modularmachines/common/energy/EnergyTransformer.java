package de.nedelosk.modularmachines.common.energy;

import de.nedelosk.modularmachines.api.energy.IEnergyTransformer;
import de.nedelosk.modularmachines.api.energy.IEnergyType;

public class EnergyTransformer implements IEnergyTransformer {

	private IEnergyType inputType;
	private IEnergyType outputType;
	private boolean divide;
	private int energyModifier;

	public EnergyTransformer(IEnergyType inputType, IEnergyType outputType, boolean divide, int energyModifier) {
		this.inputType = inputType;
		this.outputType = outputType;
		this.divide = divide;
		this.energyModifier = energyModifier;
	}

	@Override
	public long transform(long energy) {
		if(divide){
			return energy / energyModifier;
		}else{
			return energy * energyModifier;
		}
	}

	@Override
	public IEnergyType getInputType() {
		return inputType;
	}

	@Override
	public IEnergyType getOutputType() {
		return outputType;
	}
}
