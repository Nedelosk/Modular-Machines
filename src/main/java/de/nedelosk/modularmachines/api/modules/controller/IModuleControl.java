package de.nedelosk.modularmachines.api.modules.controller;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleControl {

	boolean hasPermission(IModuleState state);

	void setPermission(IModuleState state, boolean permission);

	EnumRedstoneMode getRedstoneMode();

	void setRedstoneMode(EnumRedstoneMode mode);

}
