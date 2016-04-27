package de.nedelosk.forestmods.library.modules.storage;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.library.modules.IModule;
import net.minecraft.item.ItemStack;

public interface IModuleBattery extends IModule {

	void setStorageEnergy(IModuleBattery battery, int energy, ItemStack itemStack);

	int getStorageEnergy(IModuleBattery battery, ItemStack itemStack);

	EnergyStorage getDefaultStorage();

	int getSpeedModifier();

	void setSpeedModifier(int speedModifier);

	int getBatteryCapacity();

	void setBatteryCapacity(int batteryCapacity);

	int getEnergyModifier();

	void setEnergyModifier(int energyModifier);

	EnergyStorage getStorage();

	void setStorage(EnergyStorage storage);
}
