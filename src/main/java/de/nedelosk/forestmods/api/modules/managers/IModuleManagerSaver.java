package de.nedelosk.forestmods.api.modules.managers;

import de.nedelosk.forestmods.api.modules.IModuleSaver;

public interface IModuleManagerSaver extends IModuleSaver {

	int getTab();

	void setTab(int tab);
}
