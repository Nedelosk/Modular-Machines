package nedelosk.modularmachines.api.modules.machines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.basic.IModuleWithRenderer;
import nedelosk.modularmachines.api.modules.integration.IModuleNEI;
import nedelosk.modularmachines.api.modules.integration.IModuleWaila;
import nedelosk.modularmachines.api.modules.special.IProducerController;
import nedelosk.modularmachines.api.modules.special.IProducerWithItem;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleMachine<S extends IModuleSaver>
		extends IModuleNEI<S>, IProducerWithItem<S>, IProducerController<S>, IModuleWaila<S>, IModuleWithRenderer<S> {

	int getSpeed(ModuleStack stack);

	@SideOnly(Side.CLIENT)
	String getFilePath(ModuleStack stack);
}
