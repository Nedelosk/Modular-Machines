package nedelosk.modularmachines.plugins.waila;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.plugins.waila.provider.ProviderModularMaschine;
import nedelosk.nedeloskcore.plugins.Plugin;

public class PluginWaila extends Plugin {

	@Optional.Method(modid = "Waila")
	public static void register(IWailaRegistrar registrar){
		registrar.registerBodyProvider(new ProviderModularMaschine(), TileModularMachine.class);
	}
	
	@Override
	public String getRequiredMod() {
		return "Waila";
	}
	
	@Override
	public void init(){
		FMLInterModComms.sendMessage( "Waila", "register", PluginWaila.class.getName() + ".register" );
	}
	
}
