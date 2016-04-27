package de.nedelosk.forestmods.common.blocks.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.gui.multiblocks.GuiCowper;
import de.nedelosk.forestmods.common.blocks.BlockCowper;
import de.nedelosk.forestmods.common.inventory.multiblocks.ContainerCowper;
import de.nedelosk.forestmods.common.multiblocks.cowper.MultiblockCowper;
import de.nedelosk.forestmods.library.multiblock.MultiblockValidationException;
import de.nedelosk.forestmods.library.multiblock.TileMultiblockBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

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

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if (!isConnected() || !getController().isAssembled()) {
			return null;
		}
		return new ContainerCowper(this, inventory);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (!isConnected() || !getController().isAssembled()) {
			return null;
		}
		return new GuiCowper(this, inventory);
	}
}
