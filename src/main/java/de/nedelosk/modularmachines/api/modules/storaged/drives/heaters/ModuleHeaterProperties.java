package de.nedelosk.modularmachines.api.modules.storaged.drives.heaters;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;

public class ModuleHeaterProperties extends ModuleProperties implements IModuleHeaterProperties {

	protected final double maxHeat;
	protected final int heatModifier;

	public ModuleHeaterProperties(int complexity, EnumModuleSize size, double maxHeat, int heatModifier) {
		super(complexity, size);
		this.maxHeat = maxHeat;
		this.heatModifier = heatModifier;
	}

	@Override
	public double getMaxHeat(IModuleState state){
		return maxHeat;
	}

	@Override
	public int getHeatModifier(IModuleState state){
		return heatModifier;
	}
}
