package de.nedelosk.modularmachines.api.modules.handlers;

public interface IBlockModificator extends IModuleContentHandler {

	int getMaxHeat();

	float getResistance();

	float getHardness();

	int getHarvestLevel();

	String getHarvestTool();

}
