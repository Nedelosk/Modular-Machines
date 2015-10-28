package nedelosk.modularmachines.api.modular.module.tool.producer.inventory;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.inventory.Slot;

public interface IProducerInventory extends IProducer {

	ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack);

	int getSizeInventory(ModuleStack stack);

}
