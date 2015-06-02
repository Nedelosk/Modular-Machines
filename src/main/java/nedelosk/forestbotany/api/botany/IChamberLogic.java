package nedelosk.forestbotany.api.botany;

import nedelosk.forestbotany.api.botany.IInfuserChamber.PlantStatus;

public interface IChamberLogic {

	void update();
	
	PlantStatus getPlantStatus();
	
}
