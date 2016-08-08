package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public class BlockModificator implements IBlockModificator{

	private final int maxHeat;
	private final float resistance;
	private final float hardness;
	private final int harvestLevel;
	private final String harvestTool;
	private final IModuleState state;

	public BlockModificator(IModuleState state, int maxHeat, float resistance, float hardness, String harvestTool, int harvestLevel) {
		this.state = state;
		this.maxHeat = maxHeat;
		this.resistance = resistance;
		this.hardness = hardness;
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
	}

	@Override
	public IModuleState getModuleState() {
		return state;
	}

	@Override
	public String getUID() {
		return "BlockModificator";
	}

	@Override
	public int getMaxHeat() {
		return maxHeat;
	}

	@Override
	public float getResistance() {
		return resistance;
	}

	@Override
	public float getHardness() {
		return hardness;
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}

	@Override
	public String getHarvestTool() {
		return harvestTool;
	}
}
