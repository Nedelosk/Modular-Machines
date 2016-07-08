package de.nedelosk.modularmachines.api.modules.handlers.tank;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentBuilder;
import net.minecraftforge.fluids.FluidStack;

public interface IModuleTankBuilder<M extends IModule> extends IModuleContentBuilder<FluidStack, M> {

	int addFluidTank(int capacity, boolean isInput, int xPosition, int yPosition, IContentFilter<FluidStack, M>... filters);

	@Override
	IModuleTank build();
}
