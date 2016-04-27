package de.nedelosk.forestmods.library.modules;

import de.nedelosk.forestmods.library.recipes.IMachineMode;

public interface IModuleMachineWithMode extends IModuleMachine {

	Class<? extends IMachineMode> getModeClass();

	IMachineMode getCurrentMode();

	void setCurrentMode(IMachineMode mode);
}
