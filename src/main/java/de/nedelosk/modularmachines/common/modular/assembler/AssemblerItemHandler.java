package de.nedelosk.modularmachines.common.modular.assembler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AssemblerItemHandler extends ItemStackHandler {	

	public AssemblerItemHandler() {
	}

	public AssemblerItemHandler(ItemStack[] moduleStacks) {
		super(moduleStacks);
	}

}
