package de.nedelosk.modularmachines.common.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class FakeTankManager implements ITankManager {

	private static FakeTankManager instance;

	public static FakeTankManager instance() {
		if (instance == null) {
			instance = new FakeTankManager();
		}
		return instance;
	}

	private FakeTankManager() {
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return null;
	}

	@Override
	public FluidTankSimple[] getTanks() {
		return null;
	}

	@Override
	public FluidTankSimple getTank(int position) {
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
	}
}
