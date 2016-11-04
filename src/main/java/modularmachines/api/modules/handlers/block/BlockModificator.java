package modularmachines.api.modules.handlers.block;

import modularmachines.api.modules.handlers.BlankModuleContentHandler;
import modularmachines.api.modules.state.IModuleState;

public class BlockModificator extends BlankModuleContentHandler implements IBlockModificator {

	private final int maxHeat;
	private final float resistance;
	private final float hardness;

	public BlockModificator(IModuleState moduleState, int maxHeat, float resistance, float hardness) {
		super(moduleState, "BlockModificator");
		this.maxHeat = maxHeat;
		this.resistance = resistance;
		this.hardness = hardness;
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
}
