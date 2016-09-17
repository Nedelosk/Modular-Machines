package de.nedelosk.modularmachines.api.modules.position;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;

public interface IStoragePosition{

	IModulePostion[] getPostions();

	EnumModuleSizes getSize();

	String getLocName();

	String getName();

	float getRotation();

	//Because compareTo don't work with enums
	int getProperty(IStoragePosition position);
}
