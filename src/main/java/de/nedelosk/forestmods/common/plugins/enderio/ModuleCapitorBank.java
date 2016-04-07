package de.nedelosk.forestmods.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.storage.ModuleBattery;
import net.minecraft.item.ItemStack;

public class ModuleCapitorBank extends ModuleBattery {

	public ModuleCapitorBank(String name, EnergyStorage defaultStorage) {
		super(name, defaultStorage);
	}

	@Override
	public void setStorageEnergy(ModuleStack<IModuleBattery> moduleStack, int energy, ItemStack itemStack) {
		itemStack.getTagCompound().setInteger("storedEnergyRF", energy);
	}

	@Override
	public int getStorageEnergy(ModuleStack<IModuleBattery> moduleStack, ItemStack itemStack) {
		return itemStack.getTagCompound().getInteger("storedEnergyRF");
	}
}
