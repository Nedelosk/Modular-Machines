package nedelosk.modularmachines.common.materials;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.materials.AbstractIMaterialStats;
import nedelosk.modularmachines.api.materials.Stats;
import net.minecraft.util.StatCollector;

public class EnergyConductsState extends AbstractIMaterialStats{

	public int energyConducts;
	
	public EnergyConductsState(int energyConducts) {
		super(Stats.ENERGY_CONDUCTS);
		this.energyConducts = energyConducts;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public List<String> getLocalizedInfo() {
		List<String> lists = new ArrayList();
		lists.add(StatCollector.translateToLocal("state.energy.conducts" + ": "));
		return lists;
	}

}