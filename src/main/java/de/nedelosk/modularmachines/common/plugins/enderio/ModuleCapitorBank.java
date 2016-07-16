package de.nedelosk.modularmachines.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.modules.handlers.energy.IModuleEnergyInterface;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modules.storage.ModuleBattery;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFInterface;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCapitorBank extends ModuleBattery {

	private EnergyStorage defaultStorage;

	public ModuleCapitorBank(int complexity, EnergyStorage defaultStorage) {
		super("capitorbank", complexity);
		this.defaultStorage = defaultStorage;
	}

	@Override
	public void setStorageEnergy(IModuleState state, long energy, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			itemStack.setTagCompound(new NBTTagCompound());
		}
		itemStack.getTagCompound().setLong("storedEnergyRF", energy);
	}

	@Override
	public long getStorageEnergy(IModuleState state, ItemStack itemStack) {
		if(!itemStack.hasTagCompound()){
			return 0;
		}
		return itemStack.getTagCompound().getLong("storedEnergyRF");
	}

	@Override
	public IModuleEnergyInterface getEnergyInterface(IModuleState state) {
		return new ModuleRFInterface(state, defaultStorage);
	}

	@Override
	public IEnergyType getEnergyType(IModuleState state) {
		return EnergyRegistry.redstoneFlux;
	}
}
