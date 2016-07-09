package de.nedelosk.modularmachines.api.modules;

public interface IModuleDrive extends IModule{

	/**
	 * The size of the drive. A number between 1 and 3.
	 */
	int getSize();
	
	EnumWallType getWallType();

}
