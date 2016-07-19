package de.nedelosk.modularmachines.common.plugins.cofh;

import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.common.core.ItemManager;
import de.nedelosk.modularmachines.common.energy.EnergyTransformer;
import de.nedelosk.modularmachines.common.items.ItemModuleMeta;
import de.nedelosk.modularmachines.common.plugins.APlugin;

public class PluginRF extends APlugin {

	@Override
	public void preInit() {
		EnergyRegistry.redstoneFlux = new EnergyTypeRF();
		EnergyRegistry.registerType(EnergyRegistry.redstoneFlux);

		ItemManager.itemEngineRF = ItemManager.register(new ItemModuleMeta("engineRF", new String[] { "iron", "bronze", "steel", "magmarium" }));
	}

	@Override
	public void init() {
		if(EnergyRegistry.energyUnit != null){
			EnergyRegistry.registerTransformer(new EnergyTransformer(EnergyRegistry.energyUnit, EnergyRegistry.redstoneFlux, true, 2));
			EnergyRegistry.registerTransformer(new EnergyTransformer(EnergyRegistry.redstoneFlux, EnergyRegistry.energyUnit, false, 2));
		}
	}

	@Override
	public boolean isActive() {
		try {
			Class.forName("cofh.api.energy.IEnergyProvider");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
