package de.nedelosk.forestmods.common.plugins.enderio;

import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.storage.ModuleBattery;
import net.minecraft.item.ItemStack;

public class ModuleCapitorBank extends ModuleBattery {

	public ModuleCapitorBank(String moduleUID) {
		super(moduleUID);
	}

	@Override
	public void setStorageEnergy(int energy, ModuleStack<IModuleBattery, IModuleBatterySaver> moduleStack, ItemStack itemStack) {
		itemStack.getTagCompound().setInteger("storedEnergyRF", energy);
	}

	@Override
	public int getStorageEnergy(ModuleStack<IModuleBattery, IModuleBatterySaver> moduleStack, ItemStack itemStack) {
		return itemStack.getTagCompound().getInteger("storedEnergyRF");
	}
}
