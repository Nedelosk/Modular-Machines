package nedelosk.modularmachines.api.modules.machines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.IModuleAddable;
import nedelosk.modularmachines.api.modules.basic.IModuleWithRenderer;
import nedelosk.modularmachines.api.modules.integration.IModuleNEI;
import nedelosk.modularmachines.api.modules.integration.IModuleWaila;
import nedelosk.modularmachines.api.modules.special.IModuleController;
import nedelosk.modularmachines.api.modules.special.IModuleWithItem;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleMachine extends IModuleNEI, IModuleWithItem, IModuleController, IModuleWaila, IModuleWithRenderer, IModuleAddable {

	int getSpeed(ModuleStack stack);

	@SideOnly(Side.CLIENT)
	String getFilePath(ModuleStack stack);
}
