package de.nedelosk.forestmods.api.producers;

import de.nedelosk.forestmods.api.recipes.IMachineMode;

public interface IModuleAdvancedWithMode extends IModuleAdvanced {

	Class<? extends IMachineMode> getModeClass();

	IMachineMode getCurrentMode();

	void setCurrentMode(IMachineMode mode);
}
