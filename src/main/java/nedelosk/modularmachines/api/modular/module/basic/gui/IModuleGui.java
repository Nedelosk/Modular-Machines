package nedelosk.modularmachines.api.modular.module.basic.gui;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.util.ResourceLocation;

public interface IModuleGui extends IModule {
	
	void addButtons(IGuiBase gui, IModular modular);
	
	void addWidgets(IGuiBase gui, IModular modular);
	
	int getGuiTop(IModular modular);
	
	ResourceLocation getCustomGui(IModular modular);
	
}
