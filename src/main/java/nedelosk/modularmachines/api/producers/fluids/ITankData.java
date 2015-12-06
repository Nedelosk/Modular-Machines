package nedelosk.modularmachines.api.producers.fluids;

import nedelosk.forestday.api.FluidTankBasic;
import nedelosk.forestday.api.INBTTagable;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager.TankMode;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITankData extends INBTTagable {

	void setTank(FluidTankBasic tank);
	
	FluidTankBasic getTank();
	
	void setProducer(int producer);
	
	int getProducer();
	
	void setDirection(ForgeDirection direction);
	
	ForgeDirection getDirection();
	
	void setMode(TankMode mode);
	
	TankMode getMode();
	
}
