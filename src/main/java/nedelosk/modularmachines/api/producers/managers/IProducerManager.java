package nedelosk.modularmachines.api.producers.managers;

import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.producers.special.IProducerWithItem;

public interface IProducerManager extends IProducerInventory, IProducerWithItem {

	Side getSide();

	void setSide(Side side);

	public enum Side {
		LEFT, RIGHT, BACK
	}
}
