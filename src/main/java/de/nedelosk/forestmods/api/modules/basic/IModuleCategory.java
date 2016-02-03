package de.nedelosk.forestmods.api.modules.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.container.IGuiContainer;
import de.nedelosk.forestmods.api.modules.container.IInventoryContainer;
import de.nedelosk.forestmods.api.modules.container.IModuleContainer;

public interface IModuleCategory {

	String getUID();

	Class<? extends IModuleContainer> getModuleContainerClass();

	@SideOnly(Side.CLIENT)
	Class<? extends IGuiContainer> getGuiContainerClass();

	Class<? extends IInventoryContainer> getInventoryContainerClass();
}
