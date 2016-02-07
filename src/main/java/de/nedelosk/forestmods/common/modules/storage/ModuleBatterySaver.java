package de.nedelosk.forestmods.common.modules.storage;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBatterySaver implements IModuleBatterySaver {

	private int batteryCapacity;
	private int speedModifier;
	private int energyModifier;
	public EnergyStorage storage;

	public ModuleBatterySaver() {
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		nbt.setInteger("BatteryCapacity", batteryCapacity);
		nbt.setInteger("speedModifier", speedModifier);
		nbt.setInteger("energyModifier", energyModifier);
		NBTTagCompound nbtTag = new NBTTagCompound();
		storage.writeToNBT(nbtTag);
		nbtTag.setInteger("Capacity", storage.getMaxEnergyStored());
		nbtTag.setInteger("MaxReceive", storage.getMaxReceive());
		nbtTag.setInteger("MaxExtract", storage.getMaxExtract());
		nbt.setTag("EnergyStorage", nbtTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		batteryCapacity = nbt.getInteger("BatteryCapacity");
		speedModifier = nbt.getInteger("speedModifier");
		energyModifier = nbt.getInteger("energyModifier");
		NBTTagCompound nbtTag = nbt.getCompoundTag("EnergyStorage");
		storage = new EnergyStorage(nbtTag.getInteger("Capacity"), nbtTag.getInteger("MaxReceive"), nbtTag.getInteger("MaxExtract"));
		storage.readFromNBT(nbtTag);
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public void setSpeedModifier(int speedModifier) {
		this.speedModifier = speedModifier;
	}

	@Override
	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	@Override
	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	@Override
	public int getEnergyModifier() {
		return energyModifier;
	}

	@Override
	public void setEnergyModifier(int energyModifier) {
		this.energyModifier = energyModifier;
	}

	@Override
	public EnergyStorage getStorage() {
		return storage;
	}

	@Override
	public void setStorage(EnergyStorage storage) {
		this.storage = storage;
	}
}
