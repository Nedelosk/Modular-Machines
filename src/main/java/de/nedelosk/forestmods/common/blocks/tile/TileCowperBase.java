package de.nedelosk.forestmods.common.blocks.tile;

import de.nedelosk.forestcore.multiblock.MultiblockValidationException;
import de.nedelosk.forestcore.multiblock.TileMultiblockBase;
import de.nedelosk.forestmods.common.blocks.BlockCowper;
import de.nedelosk.forestmods.common.multiblocks.cowper.MultiblockCowper;

public class TileCowperBase extends TileMultiblockBase<MultiblockCowper> {

	@Override
	public MultiblockCowper createNewMultiblock() {
		return new MultiblockCowper(worldObj);
	}

	@Override
	public Class<? extends MultiblockCowper> getMultiblockControllerType() {
		return MultiblockCowper.class;
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		if (BlockCowper.isMuffler(blockMetadata)) {
			throw new MultiblockValidationException(
					String.format("%d, %d, %d - This cowper part may not be placed in theair cowper's frame", xCoord, yCoord, zCoord));
		}
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		if (BlockCowper.isMuffler(blockMetadata)) {
			throw new MultiblockValidationException(
					String.format("%d, %d, %d - This cowper part may not be placed in theair cowper's side", xCoord, yCoord, zCoord));
		}
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		if (BlockCowper.isMuffler(blockMetadata)) {
			throw new MultiblockValidationException(
					String.format("%d, %d, %d - This cowper part may not be placed in theair cowper's bottom", xCoord, yCoord, zCoord));
		}
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This cowper part may not be placed in theair cowper's interior", xCoord, yCoord, zCoord));
	}

	@Override
	public void onMachineActivated() {
	}

	@Override
	public void onMachineDeactivated() {
	}
}
