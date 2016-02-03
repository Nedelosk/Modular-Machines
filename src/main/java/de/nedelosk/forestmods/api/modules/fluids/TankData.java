package de.nedelosk.forestmods.api.modules.fluids;

import de.nedelosk.forestcore.fluids.FluidTankSimple;
import de.nedelosk.forestmods.api.modules.managers.fluids.IModuleTankManager.TankMode;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public final class TankData {

	private FluidTankSimple tank;
	private String module;
	private ForgeDirection direction;
	private TankMode mode;
	private int capacity;

	public TankData(FluidTankSimple tank, String module, ForgeDirection direction, TankMode mode, int capacity) {
		this.tank = tank;
		tank.setCapacity(capacity);
		this.module = module;
		this.direction = direction;
		this.mode = mode;
		this.capacity = capacity;
	}

	public TankData() {
		this.tank = null;
		this.module = null;
		this.direction = ForgeDirection.UNKNOWN;
		this.mode = TankMode.NONE;
		this.capacity = 0;
	}

	public TankData(FluidTankSimple tank) {
		this.tank = tank;
		this.module = null;
		this.direction = ForgeDirection.UNKNOWN;
		this.mode = TankMode.NONE;
		this.capacity = tank.getCapacity();
	}

	public void setTank(FluidTankSimple tank) {
		this.tank = tank;
	}

	public FluidTankSimple getTank() {
		return tank;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModule() {
		return module;
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

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		if (module != null) {
			nbt.setString("Module", module);
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
			nbtTank.setInteger("Capacity", capacity);
			nbt.setTag("Tank", nbtTank);
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Module")) {
			module = nbt.getString("Module");
		}
		if (nbt.hasKey("Direction")) {
			direction = ForgeDirection.values()[nbt.getInteger("Direction")];
		}
		if (nbt.hasKey("Mode")) {
			mode = TankMode.values()[nbt.getInteger("Mode")];
		}
		if (nbt.hasKey("Tank")) {
			NBTTagCompound nbtTank = nbt.getCompoundTag("Tank");
			tank = new FluidTankSimple(capacity);
			capacity = tank.getCapacity();
			tank.readFromNBT(nbtTank);
		}
	}
}
