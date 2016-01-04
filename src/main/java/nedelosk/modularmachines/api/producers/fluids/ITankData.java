package nedelosk.modularmachines.api.producers.fluids;

import nedelosk.forestcore.library.INBTTagable;
import nedelosk.forestcore.library.fluids.FluidTankSimple;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITankData extends INBTTagable {

	void setTank(FluidTankSimple tank);

	FluidTankSimple getTank();

	void setProducer(int producer);

	int getProducer();

	void setDirection(ForgeDirection direction);

	ForgeDirection getDirection();

	void setMode(TankMode mode);

	TankMode getMode();

	public static enum TankMode {
		INPUT, OUTPUT, NONE
	}

}
