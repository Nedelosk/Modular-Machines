package de.nedelosk.modularmachines.common.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankSimple extends FluidTank {

	public FluidTankSimple(int capacity) {
		super(capacity);
	}

	public FluidTankSimple(FluidStack stack, int capacity) {
		super(stack, capacity);
	}

	public FluidTankSimple(Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
	}

	public float getFilledRatio() {
		return (float) getFluidAmount() / getCapacity();
	}

	public boolean isFull() {
		return getFluidAmount() >= getCapacity();
	}

	public boolean isEmpty() {
		return getFluidAmount() <= 0;
	}
}
