package nedelosk.modularmachines.api.modules.basic;

import nedelosk.modularmachines.api.modular.basic.container.gui.IGuiContainer;
import nedelosk.modularmachines.api.modular.basic.container.inventory.IInventoryContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IModuleContainer;

public interface IModuleCategory {

	String getUID();

	Class<? extends IModuleContainer> getModuleContainerClass();

	Class<? extends IGuiContainer> getGuiContainerClass();

	Class<? extends IInventoryContainer> getInventoryContainerClass();
}
