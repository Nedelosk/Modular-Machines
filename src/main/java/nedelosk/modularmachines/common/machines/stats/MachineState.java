package nedelosk.modularmachines.common.machines.stats;

import java.util.List;

import nedelosk.modularmachines.api.modular.material.AbstractIMaterialStats;
import nedelosk.modularmachines.api.modular.material.Stats;

public class MachineState extends AbstractIMaterialStats {

    public final int harvestLevel;
    public final int reinforced;
    public final int tier;
    public final int engineSpeed;
	
	public MachineState(int level, int reinforced, int tier, int engineSpeed) {
		super(Stats.MACHINE);
        this.harvestLevel = level;
        this.reinforced = reinforced;
        this.tier = tier;
        this.engineSpeed = engineSpeed;
	}

	@Override
	public int size() {
		return 4;
	}

	@Override
	public List<String> getLocalizedInfo(){
		return null;
	}
	
    public int harvestLevel(){
        return this.harvestLevel;
    }

    public int reinforced(){
        return this.reinforced;
    }
    
    public int tier(){
        return this.tier;
    }
    
    public int engineSpeed(){
        return this.engineSpeed;
    }

}
