package de.nedelosk.modularmachines.api.modules.storaged.drives;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;

public class ModuleKineticProperties extends ModuleProperties implements IModuleKineticProperties{

	protected final double kineticModifier;
	protected final int maxKineticEnergy;
	protected final int materialPerWork;

	public ModuleKineticProperties(int complexity, EnumModuleSize size, double kineticModifier, int maxKineticEnergy, int materialPerWork) {
		super(complexity, size);
		this.kineticModifier = kineticModifier;
		this.maxKineticEnergy = maxKineticEnergy;
		this.materialPerWork = materialPerWork;
	}

	@Override
	public double getKineticModifier(IModuleState state){
		return kineticModifier;
	}

	@Override
	public int getMaxKineticEnergy(IModuleState state){
		return maxKineticEnergy;
	}

	@Override
	public int getMaterialPerWork(IModuleState state){
		return materialPerWork;
	}

}
