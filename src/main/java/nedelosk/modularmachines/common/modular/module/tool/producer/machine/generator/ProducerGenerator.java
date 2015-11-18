package nedelosk.modularmachines.common.modular.module.tool.producer.machine.generator;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerGenerator;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachineRecipe;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerGenerator extends ProducerMachineRecipe implements IProducerGenerator {

	protected int fuel;
	protected int fuelTotal;
	protected int energy;
	
	public ProducerGenerator(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerGenerator(String modifier, int inputs, int outputs, int speed, int energy) {
		super("Generator" + modifier, inputs, outputs, speed);
		this.energy = energy;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		
		nbt.setInteger("Fuel", fuel);
		nbt.setInteger("FuelTotal", fuelTotal);
		nbt.setInteger("Energy", energy);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		
		fuel = nbt.getInteger("Fuel");
		fuelTotal = nbt.getInteger("FuelTotal");
		energy = nbt.getInteger("Energy");
	}

}
