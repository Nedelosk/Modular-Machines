package nedelosk.modularmachines.api.modular.module.tool.producer.basic;

import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;

public interface IProducerManager extends IProducerInventory {

	Side getSide();
	
	public enum Side{
		LEFT, RIGHT, BACK
	}
	
}
