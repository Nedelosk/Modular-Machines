package de.nedelosk.modularmachines.api.modules.handlers;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public interface IModuleContentHandler<M extends IModule> {

	/**
	 * @return The state of the module of the handler.
	 */
	IModuleState<M> getModuleState();

	String getUID();

	void addToolTip(List<String> tooltip, ItemStack stack, IModuleState<M> state);

	boolean isCleanable();

	/**
	 * Push the content of this handler into neighbor blocks or other modules.
	 */
	void cleanHandler(IModuleState<M> state);

	/**
	 * True when the handler has no content.
	 */
	boolean isEmpty();
}
