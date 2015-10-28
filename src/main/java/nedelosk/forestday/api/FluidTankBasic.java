package nedelosk.forestday.api;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankBasic extends FluidTank {

	public FluidTankBasic(int capacity) {
		super(capacity);
	}

	public FluidTankBasic(FluidStack stack, int capacity) {
		super(stack, capacity);
	}

	public FluidTankBasic(Fluid fluid, int amount, int capacity) {
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

	public boolean canDrainFluidType(FluidStack resource) {
		if (resource == null || resource.getFluid() == null || fluid == null) {
			return false;
		}
		return fluid.isFluidEqual(resource);
	}

	public boolean canDrainFluidType(Fluid fl) {
		if (fl == null || fluid == null) {
			return false;
		}
		return fl.getID() == fluid.getFluid().getID();
	}

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (!canDrainFluidType(resource)) {
			return null;
		}
		return drain(resource.amount, doDrain);
	}

}
