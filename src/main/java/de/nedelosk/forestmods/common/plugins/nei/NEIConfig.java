package de.nedelosk.forestmods.common.plugins.nei;

import java.util.List;

import com.google.common.collect.Lists;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.ItemInfo;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.ModuleManager;

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
			IModule module = ModuleManager.moduleRegistry.createFakeModule(container);
			if (module instanceof IModuleMachine) {
				IModuleMachine machine = (IModuleMachine) module;
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
