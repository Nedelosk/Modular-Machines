package nedelosk.modularmachines.api.producers.energy;

import cofh.api.energy.EnergyStorage;
import nedelosk.modularmachines.api.producers.client.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IProducerBattery extends IProducerGuiWithWidgets, IProducerInventory {

	EnergyStorage getStorage(ModuleStack stack);

	int getSpeedModifier();
}
