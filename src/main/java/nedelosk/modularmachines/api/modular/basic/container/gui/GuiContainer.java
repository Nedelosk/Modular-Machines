package nedelosk.modularmachines.api.modular.basic.container.gui;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleGui;

public class GuiContainer<P extends IModule> implements ISingleGuiContainer<P> {

	private IModuleGui<P> gui;

	public GuiContainer(IModuleGui<P> gui) {
		this.gui = gui;
	}

	public GuiContainer() {
	}

	@Override
	public void setGui(IModuleGui<P> gui) {
		this.gui = gui;
	}

	@Override
	public IModuleGui<P> getGui() {
		return gui;
	}
}
