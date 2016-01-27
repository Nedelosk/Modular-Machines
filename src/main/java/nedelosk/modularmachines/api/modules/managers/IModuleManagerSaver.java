package nedelosk.modularmachines.api.modules.managers;

import nedelosk.modularmachines.api.modules.IModuleSaver;

public interface IModuleManagerSaver extends IModuleSaver {

	int getTab();

	void setTab(int tab);
}
