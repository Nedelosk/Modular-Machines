package de.nedelosk.modularmachines.common.modular;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AssemblerItemHandler extends ItemStackHandler {	

	public AssemblerItemHandler() {
	}

	public AssemblerItemHandler(ItemStack[] moduleStacks) {
		super(moduleStacks);
	}

	@Override
	protected int getStackLimit(int slot, ItemStack stack) {
		return 1;
	}

	public ItemStack[] getStacks(){
		return stacks;
	}

}
