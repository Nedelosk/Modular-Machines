package nedelosk.forestcore.api.modules.manager;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidManager extends IObjectManager<Fluid> {
	
	boolean isFluidEqual(FluidStack stack);

	boolean isFluidEqual(Fluid fluid);
	
}
