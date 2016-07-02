package de.nedelosk.modularmachines.api.modular;

import net.minecraft.item.ItemStack;

public interface IAssemblerLogic {

	boolean isItemValid(ItemStack stack, IAssemblerSlot slot);

	boolean canChangeStatus(boolean isActive, IAssemblerSlot slot);

	String getUID();

}
