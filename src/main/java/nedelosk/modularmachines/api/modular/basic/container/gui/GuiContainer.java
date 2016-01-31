package nedelosk.modularmachines.api.modular.basic.container.gui;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;

public class GuiContainer<M extends IModule, S extends IModuleSaver> implements ISingleGuiContainer<M, S> {

	private IModuleGui<M, S> gui;

	public GuiContainer(IModuleGui<M, S> gui) {
		this.gui = gui;
	}

	public GuiContainer() {
	}

	@Override
	public void setGui(IModuleGui<M, S> gui) {
		this.gui = gui;
	}

	@Override
	public IModuleGui<M, S> getGui() {
		return gui;
	}
}
