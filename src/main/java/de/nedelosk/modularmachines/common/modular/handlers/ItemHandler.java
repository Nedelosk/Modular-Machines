package de.nedelosk.modularmachines.common.modular.handlers;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ItemHandler implements IItemHandler {

	public List<IItemHandler> handlers;

	public ItemHandler(List<IItemHandler> handlers) {
		this.handlers = handlers;
	}

	@Override
	public int getSlots() {
		int slots = 0;
		for(IItemHandler handler : handlers){
			slots+=handler.getSlots();
		}
		return slots;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		int slots = 0;
		for(IItemHandler handler : handlers){
			slots+=handler.getSlots();
			if(slot < slots){
				return handler.getStackInSlot(slot - (slots - handler.getSlots()));
			}
		}
		return null;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		int slots = 0;
		for(IItemHandler handler : handlers){
			slots+=handler.getSlots();
			if(slot < slots){
				return handler.insertItem(slot - (slots - handler.getSlots()), stack, simulate);
			}
		}
		return null;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		int slots = 0;
		for(IItemHandler handler : handlers){
			slots+=handler.getSlots();
			if(slot < slots){
				return handler.extractItem(slot - (slots - handler.getSlots()), amount, simulate);
			}
		}
		return null;
	}
}
