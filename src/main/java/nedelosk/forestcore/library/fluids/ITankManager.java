package nedelosk.forestcore.library.fluids;

import nedelosk.forestcore.library.INBTTagable;
import net.minecraftforge.fluids.IFluidHandler;

public interface ITankManager extends IFluidHandler, INBTTagable {

	FluidTankSimple[] getTanks();

	FluidTankSimple getTank(int position);

}
