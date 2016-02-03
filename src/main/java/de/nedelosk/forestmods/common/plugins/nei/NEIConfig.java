package de.nedelosk.forestmods.common.plugins.nei;

import java.util.List;

import com.google.common.collect.Lists;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import de.nedelosk.forestcore.modules.AModuleManager;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleRegistry.ModuleItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.core.modules.ModuleModularMachine;
import de.nedelosk.forestmods.common.plugins.nei.machines.CampfireHandler;
import de.nedelosk.forestmods.common.plugins.nei.machines.CharcoalKilnHandler;
import de.nedelosk.forestmods.common.plugins.nei.machines.WorkbenchHandler;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {

	public static boolean isAdded;
	public static List<String> producerHandlers = Lists.newArrayList();

	@Override
	public void loadConfig() {
		if (AModuleManager.isModuleLoaded("ModularMachines")) {
			isAdded = false;
			for ( ModuleItem item : ModuleRegistry.getModuleItems() ) {
				ModuleStack stack = item.moduleStack;
				if (stack.getModule() instanceof IModuleMachineRecipe) {
					String module = stack.getModule().getUID();
					if (((IModuleMachineRecipe) stack.getModule()).addNEIStacks(stack, null) != null && !producerHandlers.contains(module)) {
						new ModularMachinesHandler(stack);
						producerHandlers.add(module);
					}
				}
			}
			isAdded = true;
			API.hideItem(new ItemStack(ModuleModularMachine.BlockManager.Modular_Machine.item(), 1, 0));
			registerHandler(new ShapedModuleRecipeHandler());
		}
		if (AModuleManager.isModuleLoaded("ForestDay")) {
			registerHandler(new CharcoalKilnHandler());
			registerHandler(new WorkbenchHandler());
			registerHandler(new CampfireHandler());
		}
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
