package nedelosk.modularmachines.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachineRecipe;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {

	public static boolean isAdded;
	
	@Override
	public void loadConfig() {
		isAdded = false;
		for(ModuleStack stack : ModuleRegistry.getModuleItems())
			if(stack.getProducer() instanceof IProducerMachineRecipe)
			{
				if(((IProducerMachineRecipe)stack.getProducer()).addNEIStacks(stack) != null)
				{
					new ModularMachinesHandler(stack);
				}
			}
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
