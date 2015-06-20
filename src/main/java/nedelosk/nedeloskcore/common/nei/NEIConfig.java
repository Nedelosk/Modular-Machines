package nedelosk.nedeloskcore.common.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new PlanHandler());
		API.registerUsageHandler(new PlanHandler());
	}

	@Override
	public String getName() {
		return "Nedelosk Core NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "0.1";
	}
}
