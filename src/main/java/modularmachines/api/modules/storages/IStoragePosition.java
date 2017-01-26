package modularmachines.api.modules.storages;

import modularmachines.api.modules.EnumModuleSizes;

public interface IStoragePosition {

	EnumModuleSizes getSize();

	String getDisplayName();

	String getName();

	int compareTo(IStoragePosition position);
	
}
