package nedelosk.modularmachines.api.modular.module.tool.producer.inventory;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGui;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.inventory.Slot;

public interface IProducerInventory extends IProducerGui {

	ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack);

	int getSizeInventory(ModuleStack stack);

}
