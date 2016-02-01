package nedelosk.modularmachines.plugins.nei;

import java.util.List;

import com.google.common.collect.Lists;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import nedelosk.modularmachines.api.modules.machines.recipe.IModuleMachineRecipe;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleRegistry.ModuleItem;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.modules.ModuleModular;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {

	public static boolean isAdded;
	public static List<String> producerHandlers = Lists.newArrayList();

	@Override
	public void loadConfig() {
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
		GuiCraftingRecipe.craftinghandlers.add(new ShapedModuleRecipeHandler());
		GuiUsageRecipe.usagehandlers.add(new ShapedModuleRecipeHandler());
		API.hideItem(new ItemStack(ModuleModular.BlockManager.Modular_Machine.item(), 1, 0));
	}

	@Override
	public String getName() {
		return "Modular Machines NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "2.0";
	}
}
