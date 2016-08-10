package de.nedelosk.modularmachines.api.modules.handlers;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public interface IModuleContentHandler {

	/**
	 * @return The state of the module of the handler.
	 */
	IModuleState getModuleState();

	String getUID();

	void addToolTip(List<String> tooltip, ItemStack stack, IModuleState state);
}
