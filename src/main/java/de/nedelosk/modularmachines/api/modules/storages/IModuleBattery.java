package de.nedelosk.modularmachines.api.modules.storages;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public interface IModuleBattery extends IModule, IModuleBatteryProperties {

	void saveEnergy(IModuleState<IModuleBattery> state, long energy, ItemStack itemStack);

	long getStorageEnergy(IModuleState<IModuleBattery> state, ItemStack itemStack);
}
