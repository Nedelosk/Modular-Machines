package de.nedelosk.forestmods.common.plugins.thermalexpansion;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.forestmods.common.modules.storage.ModuleBattery;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEnergyCell extends ModuleBattery {

	public ModuleEnergyCell(IModular modular, IModuleContainer container, EnergyStorage defaultStorage) {
		super(modular, container, defaultStorage);
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
