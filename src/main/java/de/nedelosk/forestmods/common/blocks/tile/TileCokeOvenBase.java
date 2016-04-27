package de.nedelosk.forestmods.common.blocks.tile;

import de.nedelosk.forestmods.common.multiblocks.cokeoven.MultiblockCokeOven;
import de.nedelosk.forestmods.library.multiblock.MultiblockValidationException;
import de.nedelosk.forestmods.library.multiblock.TileMultiblockBase;

public abstract class TileCokeOvenBase extends TileMultiblockBase<MultiblockCokeOven> {

	@Override
	public MultiblockCokeOven createNewMultiblock() {
		return new MultiblockCokeOven(worldObj);
	}

	@Override
	public Class<? extends MultiblockCokeOven> getMultiblockControllerType() {
		return MultiblockCokeOven.class;
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This coke oven part may not be placed in theair coke oven's interior", xCoord, yCoord, zCoord));
	}

	@Override
	public void onMachineActivated() {
	}

	@Override
	public void onMachineDeactivated() {
	}
}
