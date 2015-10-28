package nedelosk.modularmachines.common.modular.module.tool.producer.fluids;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.Producer;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.IProducerTank;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerTank extends Producer implements IProducerTank {

	public int capacity;

	public ProducerTank(String modifier, int capacity) {
		super(modifier);
		this.capacity = capacity;
	}

	public ProducerTank(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);

		nbt.setInteger("Capacity", capacity);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);

		this.capacity = nbt.getInteger("Capacity");
	}

	@Override
	public int getCapacity() {
		return capacity;
	}
}
