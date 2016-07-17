package de.nedelosk.modularmachines.api.modular;

import java.util.List;

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
		return getModule(modular, IModuleCasing.class);
	}

	private static <M extends IModule> IModuleState<M> getModule(IModular modular, Class<? extends M> moduleClass) {
		if (modular == null) {
			return null;
		}
		List<IModuleState<M>> modules = modular.getModules(moduleClass);
		if (modules == null || modules.size() == 0) {
			return null;
		}
		return modules.get(0);
	}
}