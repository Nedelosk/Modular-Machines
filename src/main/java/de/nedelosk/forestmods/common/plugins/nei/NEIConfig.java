package de.nedelosk.forestmods.common.plugins.nei;

import java.util.List;

import com.google.common.collect.Lists;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import de.nedelosk.forestmods.api.utils.IModuleHandler;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.plugins.nei.machines.CampfireHandler;
import de.nedelosk.forestmods.common.plugins.nei.machines.CharcoalKilnHandler;
import de.nedelosk.forestmods.common.plugins.nei.machines.WorkbenchHandler;

public class NEIConfig implements IConfigureNEI {

	public static boolean isAdded;
	public static List<String> producerHandlers = Lists.newArrayList();

	@Override
	public void loadConfig() {
		isAdded = false;
		for ( IModuleHandler item : ModuleManager.moduleRegistry.getModuleHandlers() ) {
			ModuleStack stack = item.getModuleStack();
			if (stack.getModule() instanceof IModuleProducerRecipe) {
				String module = ((IModuleProducerRecipe) stack.getModule()).getRecipeCategory(stack);
				if (((IModuleProducerRecipe) stack.getModule()).addNEIStacks(stack, null) != null && !producerHandlers.contains(module)) {
					new ModularMachinesHandler(stack);
					producerHandlers.add(module);
				}
			}
		}
		isAdded = true;
		registerHandler(new ShapedModuleRecipeHandler());
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
		return "NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
