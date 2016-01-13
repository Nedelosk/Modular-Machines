package nedelosk.forestday.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import nedelosk.forestday.plugins.nei.machines.CampfireHandler;
import nedelosk.forestday.plugins.nei.machines.CharcoalKilnHandler;
import nedelosk.forestday.plugins.nei.machines.WorkbenchHandler;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		registerHandler(new CharcoalKilnHandler());
		registerHandler(new WorkbenchHandler());
		registerHandler(new CampfireHandler());
	}

	private <H extends ICraftingHandler & IUsageHandler> void registerHandler(H handler) {
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
	}

	@Override
	public String getName() {
		return "Forest Day NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "1.0.1";
	}
}
