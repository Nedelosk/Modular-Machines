package de.nedelosk.forestmods.api.modules.storage.battery;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IModuleBattery extends IModule {

	void setStorageEnergy(ModuleStack<IModuleBattery> stack, int energy, ItemStack itemStack);

	int getStorageEnergy(ModuleStack<IModuleBattery> stack, ItemStack itemStack);

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
