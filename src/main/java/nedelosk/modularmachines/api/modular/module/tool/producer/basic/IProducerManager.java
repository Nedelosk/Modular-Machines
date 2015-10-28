package nedelosk.modularmachines.api.modular.module.tool.producer.basic;

import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;

public interface IProducerManager extends IProducerInventory, IProducerWithItem {

	Side getSide();

	void setSide(Side side);

	public enum Side {
		LEFT, RIGHT, BACK
	}

}
