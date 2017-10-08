/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.assemblers;

import net.minecraft.item.ItemStack;

public interface IModuleSlot {
	
	/**
	 * @return the size of this slot.
	 */
	int getSize();
	
	void setSize(int size);
	
	IModuleSlots getParent();
	
	boolean isStorage();
	
	/**
	 * @return the index of this slot.
	 */
	int getIndex();
	
	ItemStack getItem();
	
	default boolean hasItem(){
		return !getItem().isEmpty();
	}
	
}
