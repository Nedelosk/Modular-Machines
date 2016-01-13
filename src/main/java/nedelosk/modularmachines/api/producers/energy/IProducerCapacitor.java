package nedelosk.modularmachines.api.producers.energy;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.IProducer;

public interface IProducerCapacitor extends IProducer {

	int getSpeedModifier();

	int getEnergyModifier();

	boolean canWork(IModular modular);
}
