package de.nedelosk.modularmachines.common.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.core.FluidManager;
import net.minecraftforge.fluids.FluidStack;

public class FluidFilterSteam implements IContentFilter<FluidStack, IModule>{
	@Override
	public boolean isValid(int index, FluidStack content, IModuleState<IModule> module) {
		return content.getFluid() == FluidManager.Steam;
	}
}
