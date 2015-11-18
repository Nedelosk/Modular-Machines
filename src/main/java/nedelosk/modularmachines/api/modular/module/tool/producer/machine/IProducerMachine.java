package nedelosk.modularmachines.api.modular.module.tool.producer.machine;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerController;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerWithItem;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;

public interface IProducerMachine extends IProducerInventory, IProducerGuiWithWidgets, IProducerWithItem, IProducerController {

	int getSpeed(ModuleStack stack);

	ArrayList<NeiStack> addNEIStacks(ModuleStack stack);

}
