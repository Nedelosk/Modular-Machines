package de.nedelosk.modularmachines.api.modular;

import net.minecraft.item.ItemStack;

public interface IAssemblerLogic {

	/**
	 * @return True, when the item is valid for the slot and this storage.
	 */
	boolean isItemValid(ItemStack stack, IAssemblerSlot slot, IModuleStorage storage);

	/**
	 * @return True, when the slot can change his status.
	 */
	boolean canChangeStatus(boolean isActive, IAssemblerSlot slot, IModuleStorage storage);


	/**
	 * @return True, when the modular can be assembled.
	 */
	boolean canAssemble(IModular modular);

	/**
	 * This UID is required to register, the logic at the ModularManager.
	 */
	String getUID();

}
