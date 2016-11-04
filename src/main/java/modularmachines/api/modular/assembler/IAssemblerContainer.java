package modularmachines.api.modular.assembler;

import modularmachines.api.gui.IContainerBase;
import modularmachines.api.modular.handlers.IModularHandler;

public interface IAssemblerContainer extends IContainerBase<IModularHandler> {

	boolean transferStack();

	boolean hasStorageChange();

	void setHasStorageChange(boolean hasChange);
}
