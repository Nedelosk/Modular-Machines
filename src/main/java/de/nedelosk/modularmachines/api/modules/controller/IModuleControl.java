package de.nedelosk.modularmachines.api.modules.controller;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleControl<M extends IModule> {

	boolean hasPermission(IModuleState<M> state);

	void setPermission(IModuleState<M> state, boolean permission);

	EnumRedstoneMode getRedstoneMode();

	void setRedstoneMode(EnumRedstoneMode mode);
}
