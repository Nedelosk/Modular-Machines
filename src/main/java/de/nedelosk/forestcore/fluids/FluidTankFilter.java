package de.nedelosk.forestcore.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankFilter extends FluidTankSimple {

	public Fluid filter;

	public FluidTankFilter(int capacity, Fluid filter) {
		super(capacity);
		this.filter = filter;
	}

	public FluidTankFilter(int capacity, String filter) {
		this(capacity, FluidRegistry.getFluid(filter));
	}

	public FluidTankFilter(int capacity) {
		super(capacity);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (filter != null) {
			String fN = FluidRegistry.getFluidName(filter);
			nbt.setString("FilterName", FluidRegistry.getFluidName(filter));
		}
		return super.writeToNBT(nbt);
	}

	@Override
	public FluidTank readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("FilterName")) {
			filter = FluidRegistry.getFluid(nbt.getString("FilterName"));
		}
		return super.readFromNBT(nbt);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource == null || filter == null || resource.getFluid() != filter) {
			return 0;
		}
		return super.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null || filter == null || resource.getFluid() != filter) {
			return null;
		}
		return super.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return super.drain(maxDrain, doDrain);
	}
}
