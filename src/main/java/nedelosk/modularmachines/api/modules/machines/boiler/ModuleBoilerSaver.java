package nedelosk.modularmachines.api.modules.machines.boiler;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.machines.ModuleMachineSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBoilerSaver extends ModuleMachineSaver implements IModuleBoilerSaver {

	protected int fuel;
	protected int fuelTotal;
	protected int heat;
	protected int heatTotal;

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("Fuel", fuel);
		nbt.setInteger("FuelTotal", fuelTotal);
		nbt.setInteger("Heat", heat);
		nbt.setInteger("HeatTotal", heatTotal);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		fuel = nbt.getInteger("Fuel");
		fuelTotal = nbt.getInteger("FuelTotal");
		heat = nbt.getInteger("Heat");
		heatTotal = nbt.getInteger("HeatTotal");
	}

	@Override
	public int getHeat() {
		return heat;
	}

	@Override
	public int getHeatTotal() {
		return heatTotal;
	}

	@Override
	public void setHeat(int heat) {
		this.heat = heat;
	}

	@Override
	public void addHeat(int heat) {
		this.heat = heat;
	}

	@Override
	public int getFuel() {
		return fuel;
	}

	@Override
	public int getFuelTotal() {
		return fuelTotal;
	}

	@Override
	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	@Override
	public void addFuel(int fuel) {
		this.fuel += fuel;
	}
}
