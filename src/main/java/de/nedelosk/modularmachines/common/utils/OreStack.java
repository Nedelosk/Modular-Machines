package de.nedelosk.modularmachines.common.utils;

import net.minecraft.nbt.NBTTagCompound;

public class OreStack {

	public String oreDict;
	public int stackSize;

	public OreStack(String oreDict) {
		this.oreDict = oreDict;
		this.stackSize = 1;
	}

	public OreStack(String oreDict, int stackSize) {
		this.oreDict = oreDict;
		this.stackSize = stackSize;
	}

	public int getStackSize() {
		return stackSize;
	}

	public String getOreDict() {
		return oreDict;
	}

	public static OreStack loadOreStackFromNBT(NBTTagCompound nbt) {
		return new OreStack(nbt.getString("oreDict"), nbt.getInteger("stackSize"));
	}

	public void readFromNBT(NBTTagCompound nbt) {
		oreDict = nbt.getString("oreDict");
		stackSize = nbt.getInteger("stackSize");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("oreDict", oreDict);
		nbt.setInteger("stackSize", stackSize);
	}
}
