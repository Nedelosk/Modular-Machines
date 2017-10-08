/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.assemblers;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.items.IItemHandlerModifiable;

public interface IModuleSlots extends Iterable<IModuleSlot>{
	
	IStoragePage getPage();
	
	boolean isItemValid(ItemStack itemStack, boolean storage);
	
	/**
	 * Calls {@link #isItemValid(ItemStack, boolean)} you not have to call that before.
	 */
	boolean addItem(int index, ItemStack itemStack);
	
	void setItem(int index, ItemStack itemStack);
	
	default ItemStack removeItem(int index){
		return removeItem(index, getItem(index));
	}
	
	ItemStack removeItem(int index, ItemStack itemStack);
	
	ItemStack getItem(int index);
	
	@Nullable
	IModuleSlot getSlot(int index);
	
	default int getFreeSpace(){
		return getSize() - getUsedSpace();
	}
	
	NBTTagCompound writeToNBT(NBTTagCompound compound);
	
	void readFromNBT(NBTTagCompound compound);
	
	void reload();
	
	int getUsedSpace();
	
	int getSize();
	
	List<IModuleSlot> getSlots();
	
	IModuleSlot getStorageSlot();
	
	IItemHandlerModifiable getItemHandler();
}
