package nedelosk.modularmachines.plugins.nei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachineRecipe;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {

	public static boolean isAdded;
	public static List<String> producerHandlers = Lists.newArrayList();

	@Override
	public void loadConfig() {
		isAdded = false;
		for (ModuleStack stack : ModuleRegistry.getModuleItems())
			if (stack.getProducer() instanceof IProducerMachineRecipe) {
				if (((IProducerMachineRecipe) stack.getProducer()).addNEIStacks(stack) != null && !producerHandlers.equals(stack.getModule().getName(stack, false))) {
					new ModularMachinesHandler(stack);
					producerHandlers.add(stack.getModule().getName(stack, false));
				}
			}
		isAdded = true;

		GuiCraftingRecipe.craftinghandlers.add(new ShapedModuleRecipeHandler());
		GuiUsageRecipe.usagehandlers.add(new ShapedModuleRecipeHandler());
		
		API.hideItem(new ItemStack(MMBlockManager.Modular_Machine.item(), 1, 0));
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
