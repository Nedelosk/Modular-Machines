package nedelosk.forestday.common.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import nedelosk.forestday.common.core.managers.FBlockManager;
import nedelosk.forestday.common.plugins.nei.machines.CampfireHandler;
import nedelosk.forestday.common.plugins.nei.machines.CharcoalKilnHandler;
import nedelosk.forestday.common.plugins.nei.machines.ResinKilnHandler;
import nedelosk.forestday.common.plugins.nei.machines.WorkbenchHandler;
import net.minecraft.item.ItemStack;

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
		
	    for(int i = 0;i < 16;i++)
	    	API.hideItem(new ItemStack(FBlockManager.Multiblock_Charcoal_Kiln.item(), 1, i));
		
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
