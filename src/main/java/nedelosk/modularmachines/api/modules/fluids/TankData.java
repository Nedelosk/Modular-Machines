package nedelosk.modularmachines.api.modules.fluids;

import nedelosk.forestcore.library.fluids.FluidTankSimple;
import nedelosk.modularmachines.api.modules.managers.fluids.IModuleTankManager.TankMode;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public final class TankData {

	private FluidTankSimple tank;
	private int producer;
	private ForgeDirection direction;
	private TankMode mode;

	public TankData(FluidTankSimple tank, int producer, ForgeDirection direction, TankMode mode) {
		this.tank = tank;
		this.producer = producer;
		this.direction = direction;
		this.mode = mode;
	}

	public TankData() {
		this.tank = null;
		this.producer = 0;
		this.direction = ForgeDirection.UNKNOWN;
		this.mode = TankMode.NONE;
	}

	public TankData(FluidTankSimple tank) {
		this.tank = tank;
		this.producer = 0;
		this.direction = ForgeDirection.UNKNOWN;
		this.mode = TankMode.NONE;
	}

	public void setTank(FluidTankSimple tank) {
		this.tank = tank;
	}

	public FluidTankSimple getTank() {
		return tank;
	}

	public void setProducer(int producer) {
		this.producer = producer;
	}

	public int getProducer() {
		return producer;
	}

	public void setDirection(ForgeDirection direction) {
		this.direction = direction;
	}

	public ForgeDirection getDirection() {
		return direction;
	}

	public void setMode(TankMode mode) {
		this.mode = mode;
	}

	public TankMode getMode() {
		return mode;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		if (producer != -1) {
			nbt.setInteger("Producer", producer);
		}
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

	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Producer")) {
			producer = nbt.getInteger("Producer");
		}
		if (nbt.hasKey("Direction")) {
			direction = ForgeDirection.values()[nbt.getInteger("Direction")];
		}
		if (nbt.hasKey("Mode")) {
			mode = TankMode.values()[nbt.getInteger("Mode")];
		}
		if (nbt.hasKey("Tank")) {
			NBTTagCompound nbtTank = nbt.getCompoundTag("Tank");
			tank = new FluidTankSimple(nbtTank.getInteger("Capacity"));
			tank.readFromNBT(nbtTank);
		}
	}
}
