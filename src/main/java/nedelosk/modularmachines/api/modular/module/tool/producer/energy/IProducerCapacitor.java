package nedelosk.modularmachines.api.modular.module.tool.producer.energy;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;

public interface IProducerCapacitor extends IProducer {

	int getSpeedModifier();
	
	int getEnergyModifier();
	
	boolean canWork(IModular modular);
	
}
