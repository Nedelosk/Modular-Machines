package de.nedelosk.forestmods.api.modules.storage.battery;

import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.modules.IModuleDropped;
import de.nedelosk.forestmods.api.modules.basic.IModuleWithRenderer;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IModuleBattery extends IModuleAddable, IModuleDropped, IModuleWithRenderer {

	void setStorageEnergy(int energy, ModuleStack<IModuleBattery, IModuleBatterySaver> moduleStack, ItemStack itemStack);

	int getStorageEnergy(ModuleStack<IModuleBattery, IModuleBatterySaver> moduleStack, ItemStack itemStack);
}
