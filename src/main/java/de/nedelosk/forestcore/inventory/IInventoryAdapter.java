package de.nedelosk.forestcore.inventory;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;

public interface IInventoryAdapter extends ISidedInventory {

	void readFromNBT(NBTTagCompound nbttagcompound);

	void writeToNBT(NBTTagCompound nbttagcompound);
}