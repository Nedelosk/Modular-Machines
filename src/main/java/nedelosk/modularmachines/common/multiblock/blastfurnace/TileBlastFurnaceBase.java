package nedelosk.modularmachines.common.multiblock.blastfurnace;

import nedelosk.forestcore.library.multiblock.MultiblockValidationException;

public class TileBlastFurnaceBase extends TileBlastFurnacePart {

	public TileBlastFurnaceBase() {
		super();
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {

	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {

	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {

	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {

	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format(
				"%d, %d, %d - This reactor part may not be placed in the reactor's interior", xCoord, yCoord, zCoord));
	}

	@Override
	public void onMachineActivated() {

	}

	@Override
	public void onMachineDeactivated() {

	}

}
