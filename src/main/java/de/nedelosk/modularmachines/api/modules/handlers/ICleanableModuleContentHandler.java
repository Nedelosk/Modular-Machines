package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface ICleanableModuleContentHandler extends IModuleContentHandler {
	
	/**
	 * Push the content of this handler into neighbor blocks.
	 */
	void cleanHandler(IModuleState state);
	
	/**
	 * True when the handler has no content.
	 */
	boolean isEmpty();
	
}
