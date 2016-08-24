package de.nedelosk.modularmachines.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.modules.handlers.energy.IModuleEnergyInterface;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModulePosition;
import de.nedelosk.modularmachines.common.modules.storaged.storage.ModuleBattery;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFInterface;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCapitorBank extends ModuleBattery {

	public ModuleCapitorBank() {
		super("capacitorbank");
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
	public IModuleEnergyInterface createEnergyInterface(IModuleState state) {
		return new ModuleRFInterface(state, new EnergyStorage(getCapacity(state), getMaxReceive(state), getMaxExtract(state)));
	}

	@Override
	public IEnergyType getEnergyType(IModuleState state) {
		return EnergyRegistry.redstoneFlux;
	}

	@Override
	public EnumModulePosition getPosition(IModuleContainer container) {
		return EnumModulePosition.INTERNAL;
	}
}
