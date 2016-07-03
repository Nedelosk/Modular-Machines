package de.nedelosk.modularmachines.api.property;

import cofh.api.energy.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;

public class PropertyEnergyStorage extends PropertyBase<EnergyStorage, NBTTagCompound, IPropertyProvider> {

	public PropertyEnergyStorage(String name, EnergyStorage defaultValue) {
		super(name, EnergyStorage.class, defaultValue);
	}

	@Override
	public NBTTagCompound writeToNBT(IPropertyProvider state, EnergyStorage value) {
		NBTTagCompound nbtTag = new NBTTagCompound();
		value.writeToNBT(nbtTag);
		nbtTag.setInteger("Capacity", value.getMaxEnergyStored());
		nbtTag.setInteger("MaxReceive", value.getMaxReceive());
		nbtTag.setInteger("MaxExtract", value.getMaxExtract());
		return nbtTag;
	}

	@Override
	public EnergyStorage readFromNBT(NBTTagCompound nbt, IPropertyProvider state) {
		NBTTagCompound nbtTag = nbt.getCompoundTag("EnergyStorage");
		EnergyStorage storage = new EnergyStorage(nbtTag.getInteger("Capacity"), nbtTag.getInteger("MaxReceive"), nbtTag.getInteger("MaxExtract"));
		storage.readFromNBT(nbtTag);
		return storage;
	}

}
