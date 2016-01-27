package nedelosk.modularmachines.api.modular.basic.container.gui;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleGui;

public interface ISingleGuiContainer<P extends IModule> extends IGuiContainer {

	void setGui(IModuleGui<P> gui);

	IModuleGui<P> getGui();
}
