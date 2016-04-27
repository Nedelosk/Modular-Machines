package de.nedelosk.forestmods.library.modules.handlers.inventory.slots;

import de.nedelosk.forestmods.library.modules.IModule;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModule extends Slot {

	public IModule module;

	public SlotModule(IModule module, int index, int xPosition, int yPosition) {
		super(module.getInventory(), index, xPosition, yPosition);
		this.module = module;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (module.getInventory().isInput(getSlotIndex())) {
			return module.getInventory().getInsertFilter().isValid(getSlotIndex(), stack, module, null);
		}
		return false;
	}
}
/*
 * package de.nedelosk.forestmods.api.modules.handlers.inventory.slots; import
 * de.nedelosk.forestmods.api.modular.IModularTileEntity; import
 * de.nedelosk.forestmods.api.utils.ModuleStack; import
 * net.minecraft.entity.player.EntityPlayer; import
 * net.minecraft.inventory.Slot; import net.minecraft.item.ItemStack; public
 * class SlotModule extends Slot { public ModuleStack moduleStack; public
 * SlotModule(IModularTileEntity inventory, int index, int xPosition, int
 * yPosition, ModuleStack stack) { super(inventory, index, xPosition,
 * yPosition); this.moduleStack = stack; }
 * @Override public ItemStack getStack() { return
 * moduleStack.getModule().getInventory().getStackInSlot(this.getSlotIndex()); }
 * @Override public void putStack(ItemStack stack) {
 * moduleStack.getModule().getInventory().setInventorySlotContents(getSlotIndex(
 * ), stack); this.onSlotChanged(); }
 * @Override public void onSlotChanged() {
 * moduleStack.getModule().getInventory().markDirty(); super.onSlotChanged(); }
 * @Override public int getSlotStackLimit() { return
 * moduleStack.getModule().getInventory().getInventoryStackLimit(); }
 * @Override public ItemStack decrStackSize(int p_75209_1_) { return
 * moduleStack.getModule().getInventory().decrStackSize(getSlotIndex(),
 * p_75209_1_); }
 * @Override public boolean isItemValid(ItemStack stack) { if
 * (moduleStack.getModule().getInventory().isInput(getSlotIndex())) { return
 * moduleStack.getModule().getInventory().getInsertFilter().isValid(getSlotIndex
 * (), stack, moduleStack, null); } return false; } }
 */