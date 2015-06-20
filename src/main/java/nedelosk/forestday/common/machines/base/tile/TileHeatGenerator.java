package nedelosk.forestday.common.machines.base.tile;

import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;

public abstract class TileHeatGenerator extends TileMachineBase {

	protected  int heat;
	protected int heatMax;
	
	public TileHeatGenerator(int slots) {
		super(slots);
	}
	
	@Override
	public void updateServer() {
		if(burnTime >= burnTimeTotal)
		{
			generateHeat();
		}
	}

	public abstract void generateHeat();
	
	public int getHeat() {
		return heat;
	}
	
	public int getHeatMax() {
		return heatMax;
	}
	
}
