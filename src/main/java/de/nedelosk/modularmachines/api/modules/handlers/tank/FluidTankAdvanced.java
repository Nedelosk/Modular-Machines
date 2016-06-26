package de.nedelosk.modularmachines.api.modules.handlers.tank;

import de.nedelosk.modularmachines.common.fluids.FluidTankSimple;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankAdvanced extends FluidTankSimple {

	private EnumTankMode mode;

	public FluidTankAdvanced(int capacity, EnumTankMode mode) {
		super(capacity);
		this.mode = mode;
	}

	public FluidTankAdvanced(int capacity, NBTTagCompound nbtTag) {
		super(capacity);
		readFromNBT(nbtTag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Mode", mode.ordinal());
		return super.writeToNBT(nbt);
	}

	@Override
	public FluidTank readFromNBT(NBTTagCompound nbt) {
		mode = EnumTankMode.values()[nbt.getInteger("Mode")];
		return super.readFromNBT(nbt);
	}

	@Override
	public boolean canDrainFluidType(FluidStack resource) {
		return super.canDrainFluidType(resource) && mode == EnumTankMode.OUTPUT;
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return super.canFillFluidType(fluid) && mode == EnumTankMode.INPUT;
	}

	public EnumTankMode getMode() {
		return mode;
	}
}
