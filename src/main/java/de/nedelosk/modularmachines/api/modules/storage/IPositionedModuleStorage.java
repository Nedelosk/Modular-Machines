package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import net.minecraft.item.ItemStack;

public interface IPositionedModuleStorage extends IModuleStorage{

	/**
	 * Add a module state to the storage and set the index of the module state.
	 */
	IModuleState addModule(ItemStack itemStack, IModuleState state);

	EnumPosition getPosition();

}
