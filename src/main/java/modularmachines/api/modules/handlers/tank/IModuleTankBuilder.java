package modularmachines.api.modules.handlers.tank;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.handlers.IModuleContentHandlerBuilder;
import modularmachines.api.modules.handlers.filters.IContentFilter;
import net.minecraftforge.fluids.FluidStack;

public interface IModuleTankBuilder<M extends IModule> extends IModuleContentHandlerBuilder<FluidStack, M> {

	int addFluidTank(int capacity, boolean isInput, int xPosition, int yPosition, IContentFilter<FluidStack, M>... filters);

	@Override
	IModuleTank build();
}