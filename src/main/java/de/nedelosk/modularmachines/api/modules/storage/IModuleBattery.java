package de.nedelosk.modularmachines.api.modules.storage;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public interface IModuleBattery extends IModuleController {

	void setStorageEnergy(IModuleBattery battery, int energy, ItemStack itemStack);

	int getStorageEnergy(IModuleBattery battery, ItemStack itemStack);

	EnergyStorage getDefaultStorage(IModuleState state);

	EnergyStorage getStorage(IModuleState state);

	void setStorage(IModuleState state, EnergyStorage storage);
}
