package nedelosk.modularmachines.common.modular.module.basic.storage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.gui.ModuleGui;
import nedelosk.modularmachines.api.modular.module.basic.storage.IModuleStorageManager;
import nedelosk.nedeloskcore.api.machines.IGuiBase;

public class ModuleStorageManager extends ModuleGui implements IModuleStorageManager {

	public ModuleStorageManager() {
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public String getModuleName() {
		return "StorageManager";
	}

}
