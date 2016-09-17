package de.nedelosk.modularmachines.api.modules.storage.module;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public interface IAddableModuleStorage extends IModuleStorage {

	/**
	 * Add a module state to the storage and set the index of the module state.
	 */
	boolean addModule(ItemStack itemStack, IModuleState state);
}
