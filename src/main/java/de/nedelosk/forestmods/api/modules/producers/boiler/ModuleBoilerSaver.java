package de.nedelosk.forestmods.api.modules.producers.boiler;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBoilerSaver implements IModuleBoilerSaver {

	protected int fuel;
	protected int fuelTotal;
	protected int heat;
	protected int heatTotal;

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		nbt.setInteger("Fuel", fuel);
		nbt.setInteger("FuelTotal", fuelTotal);
		nbt.setInteger("Heat", heat);
		nbt.setInteger("HeatTotal", heatTotal);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
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
