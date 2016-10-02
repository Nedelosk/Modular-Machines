package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.IModulePropertiesConfigurable;
import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.common.config.Configuration;

public class ModuleKineticProperties extends ModuleProperties implements IModuleKineticProperties, IModulePropertiesConfigurable{

	protected final double defaultKineticModifier;
	protected final int defaultMaxKineticEnergy;
	protected final int defaultMaterialPerWork;
	protected double kineticModifier;
	protected int maxKineticEnergy;
	protected int materialPerWork;

	public ModuleKineticProperties(int complexity, double kineticModifier, int maxKineticEnergy, int materialPerWork) {
		super(complexity);
		this.defaultKineticModifier = kineticModifier;
		this.defaultMaxKineticEnergy = maxKineticEnergy;
		this.defaultMaterialPerWork = materialPerWork;
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

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		super.processConfig(container, config);
		kineticModifier = getDouble(config, "kineticModifier", "modules." + container.getItemContainer().getRegistryName(), defaultKineticModifier, 0.0D, 100.0D, "");
		maxKineticEnergy = config.getInt("maxKineticEnergy", "modules." + container.getItemContainer().getRegistryName(), defaultMaxKineticEnergy, 0, 10000, "");
		materialPerWork = config.getInt("materialPerWork", "modules." + container.getItemContainer().getRegistryName(), defaultMaterialPerWork, 0, 1000, "");
	}

}
