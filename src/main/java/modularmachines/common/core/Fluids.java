package modularmachines.common.core;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public enum Fluids {
	STEAM;
	
	@Nullable
	private final Fluid fluid;
	
	Fluids() {
		this.fluid = FluidRegistry.getFluid("steam");
	}
	
	@Nullable
	public Fluid getFluid() {
		return fluid;
	}
	
	public boolean exists() {
		return fluid != null;
	}
	
	@Nullable
	public FluidStack get() {
		return get(Fluid.BUCKET_VOLUME);
	}
	
	@Nullable
	public FluidStack get(int amount) {
		if (fluid == null) {
			return null;
		}
		return new FluidStack(fluid, amount);
	}
}
