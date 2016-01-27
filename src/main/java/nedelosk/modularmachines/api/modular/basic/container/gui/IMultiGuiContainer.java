package nedelosk.modularmachines.api.modular.basic.container.gui;

import java.util.Collection;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleGui;

public interface IMultiGuiContainer<P extends IModule, O extends Collection<IModuleGui<P>>> extends IGuiContainer {

	void addGui(IModuleGui<P> gui);

	void addGui(int index, IModuleGui<P> gui);

	void setGuis(O collection);

	int getIndex(IModuleGui<P> gui);

	O getGuis();

	IModuleGui<P> getGui(int index);

	IModuleGui<P> getGui(String guiName);
}
