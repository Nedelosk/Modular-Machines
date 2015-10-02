package nedelosk.modularmachines.common.machines.stats;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.material.AbstractIMaterialStats;
import nedelosk.modularmachines.api.modular.material.Stats;
import net.minecraft.util.StatCollector;

public class EnergyStorageState extends AbstractIMaterialStats{

	public int energyStorage;
	
	public EnergyStorageState(int energyStorage) {
		super(Stats.ENERGY_STORAGE);
		this.energyStorage = energyStorage;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public List<String> getLocalizedInfo() {
		List<String> lists = new ArrayList();
		lists.add(StatCollector.translateToLocal("state.energy.storage" + ": "));
		return lists;
	}

}
