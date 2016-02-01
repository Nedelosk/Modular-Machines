package nedelosk.modularmachines.api.modules.container.gui;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;

public interface ISingleGuiContainer<M extends IModule, S extends IModuleSaver> extends IGuiContainer {

	void setGui(IModuleGui<M, S> gui);

	IModuleGui<M, S> getGui();
}
