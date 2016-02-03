package de.nedelosk.forestmods.common.modules.storage;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBatterySaver implements IModuleBatterySaver {

	public EnergyStorage storage;
	private int batteryCapacity;
	private int speedModifier;
	private int energyModifier;

	public ModuleBatterySaver(EnergyStorage storage) {
		this.storage = storage;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		if (storage != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			storage.writeToNBT(nbtTag);
			nbtTag.setInteger("Capacity", storage.getMaxEnergyStored());
			nbtTag.setInteger("MaxReceive", storage.getMaxReceive());
			nbtTag.setInteger("MaxExtract", storage.getMaxExtract());
			nbt.setTag("Storage", nbtTag);
		}
		nbt.setInteger("BatteryCapacity", batteryCapacity);
		nbt.setInteger("speedModifier", speedModifier);
		nbt.setInteger("energyModifier", energyModifier);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		if (nbt.hasKey("Storage")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Storage");
			storage = new EnergyStorage(nbtTag.getInteger("Capacity"), nbtTag.getInteger("MaxReceive"), nbtTag.getInteger("MaxExtract"));
			storage.readFromNBT(nbtTag);
		}
		batteryCapacity = nbt.getInteger("BatteryCapacity");
		speedModifier = nbt.getInteger("speedModifier");
		energyModifier = nbt.getInteger("energyModifier");
	}

	@Override
	public EnergyStorage getStorage(ModuleStack stack) {
		return storage;
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
}
