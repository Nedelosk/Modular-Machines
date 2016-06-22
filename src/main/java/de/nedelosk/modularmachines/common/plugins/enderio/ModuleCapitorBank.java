package de.nedelosk.modularmachines.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.modules.storage.ModuleBattery;
import net.minecraft.item.ItemStack;

public class ModuleCapitorBank extends ModuleBattery {

	public ModuleCapitorBank(EnergyStorage defaultStorage) {
		super(defaultStorage);
	}

	@Override
	public void setStorageEnergy(IModuleBattery module, int energy, ItemStack itemStack) {
		itemStack.getTagCompound().setInteger("storedEnergyRF", energy);
	}

	@Override
	public int getStorageEnergy(IModuleBattery module, ItemStack itemStack) {
		return itemStack.getTagCompound().getInteger("storedEnergyRF");
	}
}
