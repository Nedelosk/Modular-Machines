/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.assemblers;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModule extends Slot {
	private static IInventory emptyInventory = new InventoryBasic("[Null]", true, 0);
	private final IModuleSlots slots;
	private final int index;
	private final EntityPlayer player;
	@Nullable
	private final IModuleSlot slot;
	
	public SlotModule(IModuleSlot slot, int xPosition, int yPosition, EntityPlayer player)
	{
		super(emptyInventory, slot.getIndex(), xPosition, yPosition);
		this.slot = slot;
		this.slots = slot.getParent();
		this.index = slot.getIndex();
		this.player = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return slots.isItemValid(stack, isStorageSlot());
	}
	
	public boolean isStorageSlot(){
		return slot.isStorage();
	}
	
	public boolean isActive(){
		if(slot.hasItem()){
			return true;
		}
		if(isStorageSlot()){
			return true;
		}
		if(!slots.getStorageSlot().hasItem()){
			return false;
		}
		return slots.getFreeSpace() > 0 && slots.getSlot(index-1).hasItem();
	}
	
	@Override
	public ItemStack getStack() {
		return slot.getItem();
	}
	
	@Nullable
	public IModuleSlot getSlot() {
		return slot;
	}
	
	@Override
	public void putStack(ItemStack stack) {
		if(stack.isEmpty()){
			slots.removeItem(index);
		}else {
			slots.addItem(index, stack);
		}
		this.onSlotChanged();
	}
	
	@Override
	public void onSlotChange(ItemStack newStack, ItemStack originalStack) {
		if(newStack.isEmpty() && !originalStack.isEmpty()){
			slots.removeItem(index, originalStack);
		}else if(!newStack.isEmpty() && originalStack.isEmpty()){
			slots.addItem(index, newStack);
		}
	}
	
	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		IStoragePage page = slots.getPage();
		IAssembler assembler = page.getAssembler();
		page.onSlotChanged(player, assembler);
		if(isStorageSlot()) {
			assembler.onStorageSlotChange();
		}
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public ItemStack decrStackSize(int amount) {
		return slots.removeItem(index);
	}
	
	@Override
	public boolean isSameInventory(Slot other) {
		if(!(other instanceof SlotModule)){
			return false;
		}
		return slots == ((SlotModule) other).slots;
	}
}
