package nedelosk.modularmachines.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.producer.producer.recipe.IModuleProducerRecipe;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {

	public static boolean isAdded;
	
	@Override
	public void loadConfig() {
		isAdded = false;
		for(IModule module : ModuleRegistry.getModules().values())
			if(module instanceof IModuleProducerRecipe)
			{
				if(((IModuleProducerRecipe) module).addNEIStacks() != null)
				{
					new ModularMachinesHandler((IModuleProducerRecipe) module);
				}
			}
		new ModularAssemblerHandler();
		isAdded = true;
	      
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
