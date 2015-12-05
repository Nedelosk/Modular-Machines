package nedelosk.modularmachines.api.producers.fluids;

import nedelosk.forestday.api.INBTTagable;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITankManager extends INBTTagable {

	void addWidgets(Widget tank, IGuiBase gui);
	
	void setProducer(int tankID, int producer);
	
	void setProducers(int[] producer);
	
	int[] getProducers();
	
	void setTankMode(int tankID, TankMode mode);
	
	void setTankModes(TankMode[] modes);
	
	TankMode[] getTankModes();
	
	void setDirection(int tankID, ForgeDirection direction);
	
	void setDirections(ForgeDirection[] directions);
	
	ForgeDirection[] getDirections();
	
	public static enum TankMode{
		INPUT, OUTPUT
	}

}
