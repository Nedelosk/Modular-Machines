package nedelosk.modularmachines.common.producers.machines.boiler;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.machines.boiler.IModuleBoiler;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipe;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerBoiler extends ModuleMachineRecipe implements IModuleBoiler {

	protected int fuel;
	protected int fuelTotal;
	protected int steam;
	protected int water;
	protected String name;
	protected int heat;
	protected int heatTotal;

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
		nbt.setInteger("Heat", heat);
		nbt.setInteger("HeatTotal", heatTotal);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		fuel = nbt.getInteger("Fuel");
		fuelTotal = nbt.getInteger("FuelTotal");
		steam = nbt.getInteger("Steam");
		water = nbt.getInteger("Water");
		name = nbt.getString("Name");
		heat = nbt.getInteger("Heat");
		heatTotal = nbt.getInteger("HeatTotal");
	}

	@Override
	public int getHeatTotal() {
		return heatTotal;
	}

	@Override
	public int getHeat() {
		return heat;
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
