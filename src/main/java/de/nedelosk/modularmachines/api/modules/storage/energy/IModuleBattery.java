package de.nedelosk.modularmachines.api.modules.storage.energy;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModule;
import net.minecraft.item.ItemStack;

public interface IModuleBattery extends IStorageModule, IModuleBatteryProperties {

	void saveEnergy(IModuleState<IModuleBattery> state, long energy, ItemStack itemStack);

	long loadEnergy(IModuleState<IModuleBattery> state, ItemStack itemStack);
}
