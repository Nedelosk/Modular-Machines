package de.nedelosk.modularmachines.api.modules.handlers.block;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public class BlockModificator implements IModuleContentHandler, IBlockModificator{

	private final int maxHeat;
	private final float resistance;
	private final float hardness;
	private final IModuleState state;

	public BlockModificator(IModuleState state, int maxHeat, float resistance, float hardness) {
		this.state = state;
		this.maxHeat = maxHeat;
		this.resistance = resistance;
		this.hardness = hardness;
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
	public void addToolTip(List<String> tooltip, ItemStack stack, IModuleState state) {
	}
}
