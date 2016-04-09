package de.nedelosk.forestmods.api.modules.handlers.tank;

import de.nedelosk.forestcore.fluids.FluidTankSimple;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TankData implements ITankData<FluidTankSimple> {

	private FluidTankSimple tank;
	private ForgeDirection direction;
	private EnumTankMode mode;

	public TankData(FluidTankSimple tank, ForgeDirection direction, EnumTankMode mode) {
		this.tank = tank;
		this.direction = direction;
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
	public void setDirection(ForgeDirection direction) {
		this.direction = direction;
	}

	@Override
	public ForgeDirection getDirection() {
		return direction;
	}

	@Override
	public EnumTankMode getMode() {
		return mode;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (direction != null) {
			nbt.setInteger("Direction", direction.ordinal());
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
			direction = ForgeDirection.values()[nbt.getInteger("Direction")];
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
