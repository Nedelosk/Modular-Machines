package nedelosk.modularmachines.common.multiblock.blastfurnace;

import nedelosk.forestcore.library.multiblock.BlockMultiblockBasic;
import nedelosk.forestcore.library.multiblock.MultiblockValidationException;
import nedelosk.forestcore.library.multiblock.TileMultiblockBase;

public class TileBlastFurnaceBase extends TileMultiblockBase<MultiblockBlastFurnace> {

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
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This blast furnace part may not be placed in the blast furnace's interior",
						xCoord, yCoord, zCoord));
	}

	@Override
	public void onMachineActivated() {
		// Re-render controllers on client
		if (this.worldObj.isRemote) {
			if (getBlockType() instanceof BlockBlastFurnace) {
				int metadata = this.getBlockMetadata();
				if (BlockMultiblockBasic.isController(metadata)) {
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
	}

	@Override
	public void onMachineDeactivated() {
		// Re-render controllers on client
		if (this.worldObj.isRemote) {
			if (getBlockType() instanceof BlockBlastFurnace) {
				int metadata = this.getBlockMetadata();
				if (BlockMultiblockBasic.isController(metadata)) {
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
	}

	@Override
	public MultiblockBlastFurnace createNewMultiblock() {
		return new MultiblockBlastFurnace(worldObj);
	}

	@Override
	public Class<? extends MultiblockBlastFurnace> getMultiblockControllerType() {
		return MultiblockBlastFurnace.class;
	}

}
