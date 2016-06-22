package de.nedelosk.modularmachines.common.plugins.thermalexpansion;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.modules.storage.ModuleBattery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEnergyCell extends ModuleBattery {

	public ModuleEnergyCell(EnergyStorage defaultStorage) {
		super(defaultStorage);
	}

	@Override
	public void setStorageEnergy(IModuleBattery module, int energy, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			itemStack.setTagCompound(new NBTTagCompound());
		}
		itemStack.getTagCompound().setInteger("Energy", energy);
	}

	@Override
	public int getStorageEnergy(IModuleBattery module, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			return 0;
		}
		return itemStack.getTagCompound().getInteger("Energy");
	}
}
