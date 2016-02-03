package de.nedelosk.forestmods.api.modules.machines;

import de.nedelosk.forestmods.api.modules.IModuleSaver;

public interface IModuleMachineSaver extends IModuleSaver {

	int getTimer();

	void setTimer(int timer);

	void addTimer(int timer);

	int getTimerTotal();
}
