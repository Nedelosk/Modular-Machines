package nedelosk.forestday.plugins.waila;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import nedelosk.forestcore.library.plugins.APlugin;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.multiblock.TileCharcoalKiln;
import nedelosk.forestday.plugins.waila.provider.ProviderTileCampfire;
import nedelosk.forestday.plugins.waila.provider.ProviderTileCharcoalKiln;

public class PluginWaila extends APlugin {

	@Optional.Method(modid = "Waila")
	public static void register(IWailaRegistrar registrar) {
		final IWailaDataProvider tileCampfire = new ProviderTileCampfire();
		registrar.registerBodyProvider(tileCampfire, TileCampfire.class);
		final IWailaDataProvider tileKiln = new ProviderTileCharcoalKiln();
		registrar.registerBodyProvider(tileKiln, TileCharcoalKiln.class);
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
