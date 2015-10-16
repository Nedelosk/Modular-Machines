package nedelosk.modularmachines.common.modular.module.tool.producer.energy;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.Producer;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerCapacitor;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerCapacitor extends Producer implements IProducerCapacitor {

	public IModular modular;
	public int speedModifier;
	public int energyModifier;
	
	public ProducerCapacitor(String modifier, int speedModifier, int energyModifier) {
		super(modifier);
		this.speedModifier = speedModifier;
		this.energyModifier = energyModifier;
	}
	
	public ProducerCapacitor(String modifier) {
		super(modifier);
	}
	
	public ProducerCapacitor(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		this.energyModifier = nbt.getInteger("energyModifier");
		this.speedModifier = nbt.getInteger("speedModifier");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("energyModifier", energyModifier);
		nbt.setInteger("speedModifier", speedModifier);
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public int getEnergyModifier() {
		return energyModifier;
	}

	@Override
	public boolean canWork(IModular modular) {
		return true;
	}

}
