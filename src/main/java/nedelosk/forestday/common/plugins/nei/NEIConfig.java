package nedelosk.forestday.common.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import nedelosk.forestday.common.core.Defaults;
import nedelosk.forestday.common.plugins.nei.machines.CampfireHandler;
import nedelosk.forestday.common.plugins.nei.machines.CharcoalKilnHandler;
import nedelosk.forestday.common.plugins.nei.machines.ResinKilnHandler;
import nedelosk.forestday.common.plugins.nei.machines.WorkbenchHandler;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		
		API.registerRecipeHandler(new ResinKilnHandler());
		API.registerUsageHandler(new ResinKilnHandler());
		API.registerRecipeHandler(new CharcoalKilnHandler());
		API.registerUsageHandler(new CharcoalKilnHandler());
		API.registerRecipeHandler(new WorkbenchHandler());
		API.registerUsageHandler(new WorkbenchHandler());
		API.registerRecipeHandler(new CampfireHandler());
		API.registerUsageHandler(new CampfireHandler());
		
	}

	@Override
	public String getName() {
		return "Forest Day NEI Plugin";
	}

	@Override
	public String getVersion() {
		return Defaults.VERSION;
	}
}
