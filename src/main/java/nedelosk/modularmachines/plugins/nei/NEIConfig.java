package nedelosk.modularmachines.plugins.nei;

import codechicken.nei.api.IConfigureNEI;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.IModuleProducerRecipe;

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
