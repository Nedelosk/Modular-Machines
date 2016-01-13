package nedelosk.modularmachines.api.producers.managers;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.inventory.ProducerInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerManager extends ProducerInventory implements IProducerManager {

	protected Side side;

	public ProducerManager(String modifier) {
		super(modifier);
	}

	public ProducerManager(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public Side getSide() {
		return side;
	}

	@Override
	public void setSide(Side side) {
		this.side = side;
	}
}
