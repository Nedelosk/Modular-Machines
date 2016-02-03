package de.nedelosk.forestmods.common.core;

import cpw.mods.fml.common.network.IGuiHandler;
import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestcore.modules.AModuleManager;
import de.nedelosk.forestcore.plugins.APluginManager;
import de.nedelosk.forestmods.common.core.modules.ModuleManager;
import de.nedelosk.forestmods.common.plugins.PluginManager;

public class FMRegistry extends Registry {

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
		return ForestMods.proxy;
	}
}
