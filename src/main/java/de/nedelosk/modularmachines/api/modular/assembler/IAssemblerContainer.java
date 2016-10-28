package de.nedelosk.modularmachines.api.modular.assembler;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;

public interface IAssemblerContainer extends IContainerBase<IModularHandler> {

	boolean transferStack();

	boolean hasStorageChange();

	void setHasStorageChange(boolean hasChange);
}
