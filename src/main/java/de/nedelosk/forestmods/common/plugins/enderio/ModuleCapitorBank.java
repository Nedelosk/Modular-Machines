package de.nedelosk.forestmods.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.common.modules.storage.ModuleBattery;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import net.minecraft.item.ItemStack;

public class ModuleCapitorBank extends ModuleBattery {

	public ModuleCapitorBank(IModular modular, IModuleContainer container, EnergyStorage defaultStorage) {
		super(modular, container, defaultStorage);
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
