package nedelosk.modularmachines.plugins.nei;

import nedelosk.forestday.common.core.Defaults;
import nedelosk.forestday.common.plugins.nei.machines.KilnHandler;
import nedelosk.forestday.common.plugins.nei.machines.SawHandler;
import nedelosk.forestday.common.plugins.nei.machines.WorkbenchHandler;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		
	}

	@Override
	public String getName() {
		return "Modular Machines NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "0.1";
	}
}
