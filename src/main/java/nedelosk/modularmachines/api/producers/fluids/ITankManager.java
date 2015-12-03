package nedelosk.modularmachines.api.producers.fluids;

import nedelosk.forestday.api.INBTTagable;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;

public interface ITankManager extends INBTTagable {

	void addWidgets(Widget tank, IGuiBase gui);
	
	void setProducer(int tankID, String producerName);
	
	void setProducers(String[] producerName);
	
	String[] getProducers();
	
	void setTankMode(int tankID, TankMode mode);
	
	void setTankModes(TankMode[] modes);
	
	TankMode[] getTankModes();
	
	public static enum TankMode{
		INPUT, OUTPUT
	}

}
