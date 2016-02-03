package de.nedelosk.forestmods.api.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleDefault extends IModule {

	@SideOnly(Side.CLIENT)
	IModuleGui createGui(ModuleStack stack);

	IModuleInventory createInventory(ModuleStack stack);
}
