package nedelosk.forestday.common.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestcore.library.modules.AModuleManager;
import nedelosk.forestcore.library.plugins.APluginManager;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.network.GuiHandler;
import nedelosk.forestday.modules.ModuleManager;
import nedelosk.forestday.plugins.PluginManager;
import net.minecraft.creativetab.CreativeTabs;

public class FRegistry extends Registry {

	@Override
	public void preInit(Object instance, FMLPreInitializationEvent event) {
		CreativeTabs tabBlocks = Tabs.tabForestday = TabForestDay.tabForestdayBlocks;
		super.preInit(instance, event);
	}

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
		return new GuiHandler();
	}

}
