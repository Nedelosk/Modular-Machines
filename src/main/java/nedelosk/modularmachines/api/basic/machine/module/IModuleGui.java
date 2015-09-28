package nedelosk.modularmachines.api.basic.machine.module;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.util.ResourceLocation;

public interface IModuleGui extends IModule {
	
	void addButtons(IGuiBase gui, IModular modular);
	
	void addWidgets(IGuiBase gui, IModular modular);
	
	int getGuiTop(IModular modular);
	
	ResourceLocation getCustomGui(IModular modular);
	
}
