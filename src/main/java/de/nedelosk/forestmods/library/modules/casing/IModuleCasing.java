package de.nedelosk.forestmods.library.modules.casing;

import de.nedelosk.forestmods.library.modules.IModule;

public interface IModuleCasing extends IModule {

	int getMaxHeat();

	int getHeat();

	void addHeat(int heat);

	void setHeat(int heat);

	int getControllers();

	int getResistance();

	int getHardness();

	boolean canAssembleCasing();
}
