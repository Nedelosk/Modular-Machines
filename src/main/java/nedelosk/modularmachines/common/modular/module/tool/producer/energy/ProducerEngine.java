package nedelosk.modularmachines.common.modular.module.tool.producer.energy;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.Producer;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerEngine extends Producer implements IProducerEngine {

	public int speedModifier;
	
	public ProducerEngine(String modifier, int speedModifier) {
		super(modifier);
		this.speedModifier = speedModifier;
	}
	
	public ProducerEngine(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		speedModifier = nbt.getInteger("SpeedModifier");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("SpeedModifier", speedModifier);
	}

	@Override
	public int getSpeedModifier(int tier) {
		return speedModifier;
	}

}
