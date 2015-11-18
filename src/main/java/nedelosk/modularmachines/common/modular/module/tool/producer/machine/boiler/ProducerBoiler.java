package nedelosk.modularmachines.common.modular.module.tool.producer.machine.boiler;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerBoiler;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachineRecipe;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerBoiler extends ProducerMachineRecipe implements IProducerBoiler {

	protected int fuel;
	protected int fuelTotal;
	protected int steam;
	protected int water;
	
	public ProducerBoiler(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerBoiler(String modifier, int inputs, int outputs, int speed, int steam, int water) {
		super("Boiler" + modifier, inputs, outputs, speed);
		this.steam = steam;
		this.water = water;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		
		nbt.setInteger("Fuel", fuel);
		nbt.setInteger("FuelTotal", fuelTotal);
		nbt.setInteger("Steam", steam);
		nbt.setInteger("Water", water);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		
		fuel = nbt.getInteger("Fuel");
		fuelTotal = nbt.getInteger("FuelTotal");
		steam = nbt.getInteger("Steam");
		water = nbt.getInteger("Water");
	}

}
