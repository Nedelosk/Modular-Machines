package de.nedelosk.forestmods.common.inventory.slots;

import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModular<M extends IModule, S extends IModuleSaver> extends Slot {

	public ModuleStack<M, S> moduleStack;

	public SlotModular(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ModuleStack<M, S> stack) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		this.moduleStack = stack;
	}

	@Override
	public ItemStack getStack() {
		return ((IModularTileEntity<IModularDefault>) inventory).getModular().getInventoryManager().getStackInSlot(this.getSlotIndex(), moduleStack);
	}

	@Override
	public void putStack(ItemStack p_75215_1_) {
		((IModularTileEntity<IModularDefault>) inventory).getModular().getInventoryManager().setInventorySlotContents(getSlotIndex(), p_75215_1_, moduleStack);
		this.onSlotChanged();
	}

	@Override
	public int getSlotStackLimit() {
		return ((IModularTileEntity<IModularDefault>) inventory).getModular().getInventoryManager().getInventoryStackLimit(moduleStack);
	}

	@Override
	public ItemStack decrStackSize(int p_75209_1_) {
		return ((IModularTileEntity<IModularDefault>) inventory).getModular().getInventoryManager().decrStackSize(getSlotIndex(), p_75209_1_, moduleStack);
	}
}
