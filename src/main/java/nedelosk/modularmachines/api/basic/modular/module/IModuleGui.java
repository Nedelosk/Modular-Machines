package nedelosk.modularmachines.api.basic.modular.module;

import java.util.ArrayList;

import nedelosk.modularmachines.api.basic.modular.IModular;
import nedelosk.modularmachines.api.basic.modular.module.recipes.NeiStack;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;

public interface IModuleGui extends IModule {
	
	ArrayList<Slot> addSlots(IContainerBase container, IModular modular);
	
	void addButtons(IGuiBase gui, IModular modular);
	
	void addWidgets(IGuiBase gui, IModular modular);
	
	ArrayList<NeiStack> addNEIStacks();
	
}
