package nedelosk.modularmachines.common.producers.machines.boiler;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.machines.boiler.IProducerBoiler;
import nedelosk.modularmachines.api.producers.machines.recipe.ProducerMachineRecipe;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerBoiler extends ProducerMachineRecipe implements IProducerBoiler {

	protected int fuel;
	protected int fuelTotal;
	protected int steam;
	protected int water;
	protected String name;

	public ProducerBoiler(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerBoiler(String name, int inputsItem, int outputsItem, int inputsFluid, int outputsFluid, int speed, int steam, int water) {
		super("Boiler" + name, inputsItem, outputsItem, inputsFluid, outputsFluid, speed);
		this.steam = steam;
		this.water = water;
		this.name = name;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("Fuel", fuel);
		nbt.setInteger("FuelTotal", fuelTotal);
		nbt.setInteger("Steam", steam);
		nbt.setInteger("Water", water);
		nbt.setString("Name", name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		fuel = nbt.getInteger("Fuel");
		fuelTotal = nbt.getInteger("FuelTotal");
		steam = nbt.getInteger("Steam");
		water = nbt.getInteger("Water");
		name = nbt.getString("Name");
	}

	@Override
	public boolean useFluids(ModuleStack stack) {
		return true;
	}

	@Override
	public String getFilePath(ModuleStack stack) {
		return "boiler/" + name;
	}
}
