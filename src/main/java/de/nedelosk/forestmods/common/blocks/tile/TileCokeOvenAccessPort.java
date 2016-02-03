package de.nedelosk.forestmods.common.blocks.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.inventory.FakeInventoryAdapter;
import de.nedelosk.forestcore.inventory.IInventoryAdapter;
import de.nedelosk.forestcore.multiblock.MultiblockValidationException;
import de.nedelosk.forestmods.client.gui.GuiCokeOvenAccessPort;
import de.nedelosk.forestmods.common.inventory.ContainerCokeOvenAccessPort;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class TileCokeOvenAccessPort extends TileCokeOvenBase implements ISidedInventory {

	public TileCokeOvenAccessPort() {
		super();
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This coke oven part may not be placed in the coke oven's frame", xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This coke oven part may not be placed in the coke oven's side", xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This coke oven part may not be placed in the coke oven's bottom", xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This coke oven part may not be placed in the coke oven's bottom", xCoord, yCoord, zCoord));
	}

	private IInventoryAdapter getInventory() {
		if (!isConnected() && !getController().isAssembled()) {
			return FakeInventoryAdapter.instance();
		}
		return getController().getInventory();
	}

	@Override
	public int getSizeInventory() {
		return getInventory().getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return getInventory().getStackInSlot(p_70301_1_);
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		return getInventory().decrStackSize(p_70298_1_, p_70298_2_);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return getInventory().getStackInSlotOnClosing(p_70304_1_);
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		getInventory().setInventorySlotContents(p_70299_1_, p_70299_2_);
	}

	@Override
	public String getInventoryName() {
		return getInventory().getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return getInventory().hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return getInventory().getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return getInventory().isUseableByPlayer(p_70300_1_);
	}

	@Override
	public void openInventory() {
		getInventory().openInventory();
	}

	@Override
	public void closeInventory() {
		getInventory().closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return getInventory().isItemValidForSlot(p_94041_1_, p_94041_2_);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return getInventory().getAccessibleSlotsFromSide(p_94128_1_);
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return getInventory().canInsertItem(p_102007_1_, p_102007_2_, p_102007_3_);
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return getInventory().canExtractItem(p_102008_1_, p_102008_2_, p_102008_3_);
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if (!isConnected() && !getController().isAssembled()) {
			return null;
		}
		return new ContainerCokeOvenAccessPort(this, inventory);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (!isConnected() && !getController().isAssembled()) {
			return null;
		}
		return new GuiCokeOvenAccessPort(this, inventory);
	}
}
