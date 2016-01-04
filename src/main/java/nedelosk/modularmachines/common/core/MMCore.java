package nedelosk.modularmachines.common.core;

import cpw.mods.fml.common.network.IGuiHandler;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestcore.library.modules.AModuleManager;
import nedelosk.forestcore.library.plugins.APluginManager;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.modules.ModuleManager;
import nedelosk.modularmachines.plugins.PluginManager;

public class MMCore extends Registry {

	@Override
	public AModuleManager getModuleManager() {
		return new ModuleManager();
	}

	@Override
	public APluginManager getPluginManager() {
		return new PluginManager();
	}

	@Override
	public IGuiHandler getGuiHandler() {
		return ModularMachines.proxy;
	}
}
