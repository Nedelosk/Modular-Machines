package nedelosk.modularmachines.api.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleDefault extends IModule {

	@SideOnly(Side.CLIENT)
	IModuleGui createGui(ModuleStack stack);

	IModuleInventory createInventory(ModuleStack stack);
}
