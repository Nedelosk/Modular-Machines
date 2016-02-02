package nedelosk.modularmachines.api.modular.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.managers.IModularInventoryManager;

public interface IModularDefault extends IModular {

	IModularInventoryManager getInventoryManager();

	@SideOnly(Side.CLIENT)
	IModularGuiManager getGuiManager();
}
