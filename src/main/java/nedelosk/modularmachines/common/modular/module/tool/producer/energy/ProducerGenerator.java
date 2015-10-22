package nedelosk.modularmachines.common.modular.module.tool.producer.energy;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerGenerator;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachine;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerGenerator extends ProducerMachine implements IProducerGenerator {

	public ProducerGenerator(String modifier) {
		super(modifier);
	}
	
	public ProducerGenerator(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
}
