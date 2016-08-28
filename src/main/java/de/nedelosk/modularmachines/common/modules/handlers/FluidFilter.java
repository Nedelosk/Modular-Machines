package de.nedelosk.modularmachines.common.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.filters.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidFilter implements IContentFilter<FluidStack, IModule>{

	private Fluid fluid;

	public FluidFilter(Fluid fluid) {
		this.fluid = fluid;
	}

	@Override
	public boolean isValid(int index, FluidStack content, IModuleState<IModule> module) {
		return content.getFluid() == fluid;
	}
}
