package de.nedelosk.forestmods.api.modular.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.managers.IModularGuiManager;
import de.nedelosk.forestmods.api.modular.managers.IModularInventoryManager;

public interface IModularDefault extends IModular {

	IModularInventoryManager getInventoryManager();

	@SideOnly(Side.CLIENT)
	IModularGuiManager getGuiManager();
}
