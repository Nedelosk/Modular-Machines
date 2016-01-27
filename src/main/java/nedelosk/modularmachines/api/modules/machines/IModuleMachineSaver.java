package nedelosk.modularmachines.api.modules.machines;

import nedelosk.modularmachines.api.modules.IModuleSaver;

public interface IModuleMachineSaver extends IModuleSaver {

	int getTimer();

	void setTimer(int timer);

	void addTimer(int timer);

	int getTimerTotal();
}
