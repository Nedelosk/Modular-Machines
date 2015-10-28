package nedelosk.modularmachines.api.modular.module.tool.producer.energy;

import cofh.api.energy.EnergyStorage;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;

public interface IProducerBattery extends IProducerGuiWithWidgets, IProducerInventory {

	EnergyStorage getStorage(ModuleStack stack);

	int getSpeedModifier();

}
