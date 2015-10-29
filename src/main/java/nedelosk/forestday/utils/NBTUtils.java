package nedelosk.forestday.utils;

import nedelosk.forestday.api.FluidTankBasic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;

public class NBTUtils {

	public static void writeTankToNBT(String nbtName, NBTTagCompound nbt, FluidTank tank) {
		if (tank != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setInteger("Capacity", tank.getCapacity());
			tank.writeToNBT(nbtTag);
			nbt.setTag(nbtName, nbtTag);
		}
	}

	public static void readTankFromNBT(String nbtName, NBTTagCompound nbt, FluidTank tank) {
		if (nbt.hasKey(nbtName)) {
			NBTTagCompound nbtTag = nbt.getCompoundTag(nbtName);
			tank = new FluidTankBasic(nbtTag.getInteger("Capacity"));
			tank.readFromNBT(nbtTag);
		}
	}

}