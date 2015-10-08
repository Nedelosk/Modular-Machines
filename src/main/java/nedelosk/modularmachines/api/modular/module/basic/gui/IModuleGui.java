package nedelosk.modularmachines.api.modular.module.basic.gui;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import net.minecraft.util.ResourceLocation;

public interface IModuleGui extends IModule {
	
	int getGuiTop(IModular modular);
	
	ResourceLocation getCustomGui(IModular modular);
	
}
