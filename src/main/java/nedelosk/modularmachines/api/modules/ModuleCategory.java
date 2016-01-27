package nedelosk.modularmachines.api.modules;

import nedelosk.modularmachines.api.modular.basic.container.gui.IGuiContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IModuleContainer;

public class ModuleCategory implements IModuleCategory {

	private final String UID;
	private final Class<? extends IModuleContainer> moduleContainer;
	private final Class<? extends IGuiContainer> guiContainer;

	public ModuleCategory(String UID, Class<? extends IModuleContainer> moduleContainer, Class<? extends IGuiContainer> guiContainer) {
		this.UID = UID;
		this.moduleContainer = moduleContainer;
		this.guiContainer = guiContainer;
	}

	public ModuleCategory(String UID, Class<? extends IModuleContainer> moduleContainer) {
		this.UID = UID;
		this.moduleContainer = moduleContainer;
		this.guiContainer = null;
	}

	@Override
	public String getCategoryUID() {
		return UID;
	}

	@Override
	public Class<? extends IModuleContainer> getModuleContainerClass() {
		return moduleContainer;
	}

	@Override
	public Class<? extends IGuiContainer> getGuiContainerClass() {
		return guiContainer;
	}
}
