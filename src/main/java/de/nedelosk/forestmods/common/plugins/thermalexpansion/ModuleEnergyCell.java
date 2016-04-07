package de.nedelosk.forestmods.common.plugins.thermalexpansion;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.api.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.storage.ModuleBattery;
import net.minecraft.item.ItemStack;

public class ModuleEnergyCell extends ModuleBattery {

	public ModuleEnergyCell(String name, EnergyStorage defaultStorage) {
		super(name, defaultStorage);
	}

	@Override
	public void setStorageEnergy(ModuleStack<IModuleBattery> moduleStack, int energy, ItemStack itemStack) {
		itemStack.getTagCompound().setInteger("Energy", energy);
	}

	@Override
	public int getStorageEnergy(ModuleStack<IModuleBattery> moduleStack, ItemStack itemStack) {
		return itemStack.getTagCompound().getInteger("Energy");
	}
}
