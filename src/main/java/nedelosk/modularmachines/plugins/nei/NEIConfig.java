package nedelosk.modularmachines.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.basic.machine.module.IModule;
import nedelosk.modularmachines.api.basic.machine.module.IModuleProducerRecipe;
import nedelosk.modularmachines.common.core.MMBlocks;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {

	public static boolean isAdded;
	
	@Override
	public void loadConfig() {
		isAdded = false;
		for(IModule module : ModularMachinesApi.getModules().values())
			if(module instanceof IModuleProducerRecipe)
			{
				if(((IModuleProducerRecipe) module).addNEIStacks() != null)
				{
					new ModularMachinesHandler((IModuleProducerRecipe) module);
				}
			}
		isAdded = true;
		
	      GuiCraftingRecipe.craftinghandlers.add(new ShapedModularCraftingHandler());
	      GuiUsageRecipe.usagehandlers.add(new ShapedModularCraftingHandler());
	      GuiCraftingRecipe.craftinghandlers.add(new ShapelessModularCraftingHandler());
	      GuiUsageRecipe.usagehandlers.add(new ShapelessModularCraftingHandler());
	      
	      API.hideItem(new ItemStack(MMBlocks.Modular_Machine.item(), 1, 0));
	}

	@Override
	public String getName() {
		return "Modular Machines NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "0.1";
	}
}
