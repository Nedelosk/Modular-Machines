package de.nedelosk.modularmachines.common.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TankManager implements ITankManager {

	protected FluidTankSimple[] tanks;
	protected boolean canFill;
	protected boolean canDrain;

	public TankManager(NBTTagCompound nbtTag) {
		readFromNBT(nbtTag);
	}

	public TankManager(FluidTankSimple... tanks) {
		this.tanks = tanks;
		this.canDrain = true;
		this.canFill = true;
	}

	public TankManager(boolean canFill, boolean canDrain, FluidTankSimple... tanks) {
		this.tanks = tanks;
		this.canDrain = canDrain;
		this.canFill = canFill;
	}

	@Override
	public FluidTankSimple[] getTanks() {
		return tanks;
	}

	@Override
	public FluidTankSimple getTank(int position) {
		return tanks[position];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (tanks != null) {
			nbt.setInteger("Size", tanks.length);
			NBTTagList listTag = new NBTTagList();
			for(int i = 0; i < tanks.length; i++) {
				FluidTankSimple tank = tanks[i];
				if (tank != null) {
					NBTTagCompound nbtTag = new NBTTagCompound();
					nbtTag.setInteger("Capacity", tank.getCapacity());
					tank.writeToNBT(nbtTag);
					nbtTag.setInteger("Position", i);
					nbtTag.setBoolean("Filter", tank instanceof FluidTankFilter);
					listTag.appendTag(nbtTag);
				}
			}
			nbt.setTag("Tanks", listTag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Size")) {
			tanks = new FluidTankSimple[nbt.getInteger("Size")];
			NBTTagList listTag = nbt.getTagList("Tanks", 10);
			for(int i = 0; i < listTag.tagCount(); i++) {
				NBTTagCompound nbtTag = listTag.getCompoundTagAt(i);
				FluidTankSimple tank;
				if (nbtTag.getBoolean("Filter")) {
					tank = new FluidTankFilter(nbtTag.getInteger("Capacity"));
				} else {
					tank = new FluidTankSimple(nbtTag.getInteger("Capacity"));
				}
				tank.readFromNBT(nbtTag);
				int position = nbtTag.getInteger("Position");
				tanks[position] = tank;
			}
		}
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		if (tanks == null) {
			return 0;
		}
		for(FluidTankSimple tank : tanks) {
			if (tank == null || tank.isFull()) {
				continue;
			}
			if (tank.isEmpty() || tank.getFluid().getFluid() == resource.getFluid()) {
				return tank.fill(resource, doFill);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (tanks == null) {
			return null;
		}
		for(FluidTankSimple tank : tanks) {
			if (tank == null || tank.isEmpty()) {
				continue;
			}
			if (tank.getFluid().getFluid() == resource.getFluid()) {
				return tank.drain(resource, doDrain);
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		if (tanks == null) {
			return null;
		}
		for(FluidTankSimple tank : tanks) {
			if (tank == null || tank.isEmpty()) {
				continue;
			}
			if (tank.getFluid().amount > maxDrain) {
				return tank.drain(maxDrain, doDrain);
			}
		}
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return canFill;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return canDrain;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		if (tanks == null) {
			return null;
		}
		FluidTankInfo[] infos = new FluidTankInfo[tanks.length];
		for(int i = 0; i < tanks.length; i++) {
			FluidTankSimple tank = tanks[i];
			if (tank == null) {
				infos[i] = null;
			}
			infos[i] = tanks[i].getInfo();
		}
		return infos;
	}
}
