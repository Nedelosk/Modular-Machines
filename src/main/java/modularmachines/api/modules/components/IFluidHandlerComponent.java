package modularmachines.api.modules.components;

import javax.annotation.Nullable;
import java.util.function.Predicate;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;

public interface IFluidHandlerComponent extends IModuleComponent, IFluidHandler, INBTReadable, INBTWritable {
	
	default ITank addTank(int capacity) {
		return addTank(capacity, false);
	}
	
	ITank addTank(int capacity, boolean isOutput);
	
	int fillInternal(FluidStack resource, boolean doFill);
	
	@Nullable
	FluidStack drainInternal(FluidStack resource, boolean doDrain);
	
	@Nullable
	FluidStack drainInternal(int maxDrain, boolean doDrain);
	
	interface ITank extends IFluidTank {
		int getIndex();
		
		void setFluid(@Nullable FluidStack fluidStack);
		
		void setFilter(Predicate<FluidStack> filter);
		
		Predicate<FluidStack> getFilter();
	}
}
