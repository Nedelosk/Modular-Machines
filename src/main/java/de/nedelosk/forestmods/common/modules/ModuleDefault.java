package de.nedelosk.forestmods.common.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.IModuleDefault;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.inventory.ModuleInventoryDefault;

public class ModuleDefault extends Module implements IModuleDefault {

	public ModuleDefault(String categoryUID, String moduleUID) {
		super(categoryUID, moduleUID);
	}

	@Override
	public IModuleInventory createInventory(ModuleStack stack) {
		return new ModuleInventoryDefault(getUID(), 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return null;
	}
}