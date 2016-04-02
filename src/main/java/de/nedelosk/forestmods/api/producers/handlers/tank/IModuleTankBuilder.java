package de.nedelosk.forestmods.api.producers.handlers.tank;

import de.nedelosk.forestmods.api.producers.handlers.IModuleContentBuilder;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public interface IModuleTankBuilder extends IModuleContentBuilder<FluidStack> {

	void initTank(int index, int capacity, ForgeDirection direction, EnumTankMode mode);

	@Override
	IModuleTank build();
}
