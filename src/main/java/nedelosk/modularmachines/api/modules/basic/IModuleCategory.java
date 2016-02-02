package nedelosk.modularmachines.api.modules.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.container.gui.IGuiContainer;
import nedelosk.modularmachines.api.modules.container.inventory.IInventoryContainer;
import nedelosk.modularmachines.api.modules.container.module.IModuleContainer;

public interface IModuleCategory {

	String getUID();

	Class<? extends IModuleContainer> getModuleContainerClass();

	@SideOnly(Side.CLIENT)
	Class<? extends IGuiContainer> getGuiContainerClass();

	Class<? extends IInventoryContainer> getInventoryContainerClass();
}
