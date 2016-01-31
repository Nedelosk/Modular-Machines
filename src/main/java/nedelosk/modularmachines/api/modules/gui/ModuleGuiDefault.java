package nedelosk.modularmachines.api.modules.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

@SideOnly(Side.CLIENT)
public class ModuleGuiDefault<M extends IModule, S extends IModuleSaver> extends ModuleGui<M, S> {

	public ModuleGuiDefault(String UID) {
		super(UID);
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}
}
