/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.assembler;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.assemblers.IModuleSlot;
import modularmachines.api.modules.assemblers.IModuleSlots;

public class ModuleSlot implements IModuleSlot {
	private final IModuleSlots parent;
	private final int index;
	private final boolean isStorage;
	private int size;
	
	public ModuleSlot(ModuleSlots parent, int index, boolean isStorage, int size) {
		this.parent = parent;
		this.index = index;
		this.isStorage = isStorage;
		this.size = size;
	}
	
	@Override
	public IModuleSlots getParent() {
		return parent;
	}
	
	@Override
	public boolean isStorage() {
		return isStorage;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public ItemStack getItem() {
		return parent.getItem(index);
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public void setSize(int size) {
		this.size = size;
	}
}
