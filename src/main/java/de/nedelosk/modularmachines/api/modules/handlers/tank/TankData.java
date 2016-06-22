package de.nedelosk.modularmachines.api.modules.handlers.tank;

import de.nedelosk.modularmachines.common.fluids.FluidTankSimple;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TankData implements ITankData<FluidTankSimple> {

	private FluidTankSimple tank;
	private EnumFacing facing;
	private EnumTankMode mode;

	public TankData(FluidTankSimple tank, EnumFacing direction, EnumTankMode mode) {
		this.tank = tank;
		this.facing = direction;
		this.mode = mode;
	}

	@Override
	public void setTank(FluidTankSimple tank) {
		this.tank = tank;
	}

	@Override
	public FluidTankSimple getTank() {
		return tank;
	}

	@Override
	public void setFacing(EnumFacing direction) {
		this.facing = direction;
	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	@Override
	public EnumTankMode getMode() {
		return mode;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (facing != null) {
			nbt.setInteger("Facing", facing.ordinal());
		}
		if (mode != null) {
			nbt.setInteger("Mode", mode.ordinal());
		}
		if (tank != null) {
			NBTTagCompound nbtTank = new NBTTagCompound();
			tank.writeToNBT(nbtTank);
			nbtTank.setInteger("Capacity", tank.getCapacity());
			nbt.setTag("Tank", nbtTank);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Direction")) {
			facing = EnumFacing.values()[nbt.getInteger("Facing")];
		}
		if (nbt.hasKey("Mode")) {
			mode = EnumTankMode.values()[nbt.getInteger("Mode")];
		}
		if (nbt.hasKey("Tank")) {
			NBTTagCompound nbtTank = nbt.getCompoundTag("Tank");
			tank = new FluidTankSimple(nbt.getInteger("Capacity"));
			tank.readFromNBT(nbtTank);
		}
	}
}
