package nedelosk.modularmachines.api.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleDefault extends Module implements IModuleDefault {

	public ModuleDefault(String categoryUID, String moduleUID) {
		super(categoryUID, moduleUID);
	}

	@Override
	public IModuleInventory createInventory(ModuleStack stack) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return null;
	}
}
