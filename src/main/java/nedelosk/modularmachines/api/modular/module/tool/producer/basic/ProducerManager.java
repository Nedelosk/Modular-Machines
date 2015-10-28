package nedelosk.modularmachines.api.modular.module.tool.producer.basic;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.ProducerGui;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerManager extends ProducerGui implements IProducerManager {

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
