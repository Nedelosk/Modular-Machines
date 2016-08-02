package de.nedelosk.modularmachines.api.modular;

import java.util.List;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ModularUtils {

	public static IModularHandler getModularHandler(ICapabilityProvider provider){
		if(provider == null){
			return null;
		}
		if(provider.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)){
			return provider.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
		}
		return null;	
	}

	public static IModuleState<IModuleCasing> getCasing(IModular modular) {
		return getFirstModule(modular, IModuleCasing.class);
	}

	public static <M extends IModule> IModuleState<M> getFirstModule(IModular modular, Class<? extends M> moduleClass) {
		if (modular == null) {
			return null;
		}
		List<IModuleState<M>> modules = modular.getModules(moduleClass);
		if (modules == null || modules.isEmpty()) {
			return null;
		}
		return modules.get(0);
	}
}
