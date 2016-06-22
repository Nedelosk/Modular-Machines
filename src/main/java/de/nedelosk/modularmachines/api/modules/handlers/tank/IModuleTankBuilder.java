package de.nedelosk.modularmachines.api.modules.handlers.tank;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentBuilder;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

public interface IModuleTankBuilder<M extends IModule> extends IModuleContentBuilder<FluidStack, M> {

	void initTank(int index, int capacity, EnumFacing direction, EnumTankMode mode, IContentFilter<FluidStack, M>... filters);

	@Override
	IModuleTank build();
}
