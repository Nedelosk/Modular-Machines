package nedelosk.modularmachines.common.producers.machines.boiler;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.machines.boiler.IProducerBoiler;
import nedelosk.modularmachines.api.producers.machines.recipe.ProducerMachineRecipe;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerBoiler extends ProducerMachineRecipe implements IProducerBoiler {

	protected int fuel;
	protected int fuelTotal;
	protected int steam;
	protected int water;
	
	public ProducerBoiler(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerBoiler(String modifier, int inputsItem, int outputsItem, int inputsFluid, int outputsFluid, int speed, int steam, int water) {
		super("Boiler" + modifier, inputsItem, outputsItem, inputsFluid, outputsFluid, speed);
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
	
	@Override
	public boolean useFluids(ModuleStack stack) {
		return true;
	}
}
