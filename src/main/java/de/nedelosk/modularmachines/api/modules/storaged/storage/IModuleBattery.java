package de.nedelosk.modularmachines.api.modules.storaged.storage;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public interface IModuleBattery extends IModule {

	void setStorageEnergy(IModuleState<IModuleBattery> state, long energy, ItemStack itemStack);

	long getStorageEnergy(IModuleState<IModuleBattery> state, ItemStack itemStack);
}
