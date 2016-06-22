package de.nedelosk.modularmachines.common.plugins.nei;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.common.core.BlockManager;

public class NEIConfig implements IConfigureNEI {

	public static boolean isAdded;
	private static List<String> producerHandlers = Lists.newArrayList();

	@Override
	public void loadConfig() {
		ItemInfo.hiddenItems.with(BlockManager.blockCampfire, BlockManager.blockModular);
		registerHandler(new CharcoalKilnHandler());
		registerHandler(new CampfireHandler());
		registerHandler(new ShapedModuleRecipeHandler());
		registerHandler(new CraftingRecipeKilnHandler());
		isAdded = false;
		for(IModuleContainer container : ModuleManager.moduleRegistry.getModuleContainers()) {
			IModule module = ModuleManager.moduleRegistry.getFakeModule(container);
			if (module instanceof IModuleTool) {
				IModuleTool machine = (IModuleTool) module;
				if (!producerHandlers.contains(machine.getRecipeCategory())) {
					new ModularMachinesHandler(machine.getRecipeCategory(), machine.createNEIPage(machine));
					producerHandlers.add(machine.getRecipeCategory());
				}
			}
		}
		isAdded = true;
	}

	private <H extends ICraftingHandler & IUsageHandler> void registerHandler(H handler) {
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
	}

	@Override
	public String getName() {
		return "NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
