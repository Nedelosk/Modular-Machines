package nedelosk.modularmachines.plugins.waila;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;
import nedelosk.forestday.plugins.basic.Plugin;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.plugins.waila.provider.ProviderModular;

public class PluginWaila extends Plugin {

	@Optional.Method(modid = "Waila")
	public static void register(IWailaRegistrar registrar){
		registrar.registerBodyProvider(new ProviderModular(), TileModular.class);
	}
	
	@Override
	public String getRequiredMod() {
		return "Waila";
	}
	
	@Override
	public void init(){
		FMLInterModComms.sendMessage( "Waila", "register", PluginWaila.class.getName() + ".register" );
	}
	
	@Override
	public boolean getConfigOption() {
		return ModularConfig.pluginWaila;
	}
	
}
