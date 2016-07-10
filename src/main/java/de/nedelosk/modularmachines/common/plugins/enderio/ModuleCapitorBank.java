package de.nedelosk.modularmachines.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.modules.storage.ModuleBattery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCapitorBank extends ModuleBattery {

	public ModuleCapitorBank(int complexity, EnergyStorage defaultStorage) {
		super("capitorbank", complexity, defaultStorage);
	}

	@Override
	public void setStorageEnergy(IModuleBattery module, int energy, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			itemStack.setTagCompound(new NBTTagCompound());
		}
		itemStack.getTagCompound().setInteger("storedEnergyRF", energy);
	}

	@Override
	public int getStorageEnergy(IModuleBattery module, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			return 0;
		}
		return itemStack.getTagCompound().getInteger("storedEnergyRF");
	}
}
