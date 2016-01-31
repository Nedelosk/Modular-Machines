package nedelosk.modularmachines.api.modules.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;

@SideOnly(Side.CLIENT)
public class ModuleGuiDefault<M extends IModule<S>, S extends IModuleSaver> extends ModuleGui<M, S> {

	public ModuleGuiDefault(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}
}
