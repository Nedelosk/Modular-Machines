package nedelosk.forestbotany.api.soil;

import nedelosk.forestbotany.common.soil.SoilType;
import nedelosk.nedeloskcore.api.INBTTagable;

public interface ISoil extends INBTTagable {

	public SoilType getSoil();
	
	int getMaxWaterStorage();
	
	int getMaxNutrientsStorage();
	
	int getWaterStorage();
	
	int getNutrientsStorage();
	
	int addWaterToSoil(int amount);
	
	int getWaterFromSoil(int amount);
	
	int addNutrientsToSoil(int amount);
	
	int getNutrientsFromSoil(int amount);
	
}
