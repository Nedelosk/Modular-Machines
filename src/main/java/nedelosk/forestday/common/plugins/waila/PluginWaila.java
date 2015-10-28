package nedelosk.forestday.common.plugins.waila;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.blocks.tiles.TileKiln;
import nedelosk.forestday.common.plugins.waila.provider.machines.ProviderTileCampfire;
import nedelosk.forestday.common.plugins.waila.provider.machines.ProviderTileKiln;
import nedelosk.forestday.plugins.basic.Plugin;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;

public class PluginWaila extends Plugin {

	@Optional.Method(modid = "Waila")
	public static void register(IWailaRegistrar registrar) {
		final IWailaDataProvider tileKiln = new ProviderTileKiln();

		registrar.registerBodyProvider(tileKiln, TileKiln.class);

		final IWailaDataProvider tileCampfire = new ProviderTileCampfire();

		registrar.registerBodyProvider(tileCampfire, TileCampfire.class);

	}

	@Override
	public String getRequiredMod() {
		return "Waila";
	}

	@Override
	public void init() {
		FMLInterModComms.sendMessage("Waila", "register", PluginWaila.class.getName() + ".register");
	}

}
