package modularmachines.api.modules.storage.energy;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.IStorageModule;

public interface IModuleBattery extends IStorageModule, IModuleBatteryProperties {

	void saveEnergy(IModuleState<IModuleBattery> state, long energy, ItemStack itemStack);

	long loadEnergy(IModuleState<IModuleBattery> state, ItemStack itemStack);
}
