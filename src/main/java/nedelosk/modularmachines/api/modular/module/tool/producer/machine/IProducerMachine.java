package nedelosk.modularmachines.api.modular.module.tool.producer.machine;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;

public interface IProducerMachine extends IProducerInventory, IProducerGuiWithWidgets {

	int getSpeed(ModuleStack stack);
	
	int getBurnTimeTotal(IModular modular, ModuleStack stack);
	
	int getBurnTime(ModuleStack stack);
	
	int getBurnTimeTotal(ModuleStack stack);
	
	ArrayList<NeiStack> addNEIStacks(ModuleStack stack);
	
}
