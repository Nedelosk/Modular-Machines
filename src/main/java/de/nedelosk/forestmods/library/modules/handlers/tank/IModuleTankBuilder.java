package de.nedelosk.forestmods.library.modules.handlers.tank;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.library.modules.handlers.IModuleContentBuilder;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public interface IModuleTankBuilder<M extends IModule> extends IModuleContentBuilder<FluidStack, M> {

	void initTank(int index, int capacity, ForgeDirection direction, EnumTankMode mode, IContentFilter<FluidStack, M>... filters);

	@Override
	IModuleTank build();
}
