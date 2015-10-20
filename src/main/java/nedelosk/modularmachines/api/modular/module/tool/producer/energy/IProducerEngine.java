package nedelosk.modularmachines.api.modular.module.tool.producer.energy;

import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;

public interface IProducerEngine extends IProducer {

	int getSpeedModifier(int tier);
	
	String getMode();
	
}
