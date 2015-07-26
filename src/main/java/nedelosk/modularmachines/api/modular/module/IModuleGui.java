package nedelosk.modularmachines.api.modular.module;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;

public interface IModuleGui extends IModule {
	
	void addSlots(IContainerBase container, IModular modular);
	
	void addButtons(IGuiBase gui, IModular modular);
	
	void addWidgets(IGuiBase gui, IModular modular);
	
}
