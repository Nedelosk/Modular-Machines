package nedelosk.modularmachines.api.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.utils.ModuleStack;

@SideOnly(Side.CLIENT)
public class ModuleDefaultGui<P extends IModule> extends ModuleGui<P> {

	public ModuleDefaultGui(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}
}
