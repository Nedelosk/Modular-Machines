package de.nedelosk.forestmods.common.plugins.nei;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.ItemInfo;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.modules.IModuleAdvanced;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleType;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.core.BlockManager;

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
		for(Entry<IMaterial, Map<ModuleUID, ModuleType>> modulesEntry : ModuleManager.moduleRegistry.getModules().entrySet()) {
			for(Entry<ModuleUID, ModuleType> entry : modulesEntry.getValue().entrySet()) {
				ModuleStack module = new ModuleStack(entry.getKey(), modulesEntry.getKey());
				if (module.getModule() instanceof IModuleAdvanced) {
					IModuleAdvanced advanced = (IModuleAdvanced) module.getModule();
					if (!producerHandlers.contains(advanced.getRecipeCategory())) {
						new ModularMachinesHandler(advanced.getRecipeCategory(), advanced.createNEIPage(module));
						producerHandlers.add(advanced.getRecipeCategory());
					}
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
