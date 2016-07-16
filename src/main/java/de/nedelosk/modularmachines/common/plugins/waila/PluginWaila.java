package de.nedelosk.modularmachines.common.plugins.waila;

import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import de.nedelosk.modularmachines.common.plugins.waila.provider.ProviderModular;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInterModComms;

@Optional.Interface(modid = "Waila", iface = "mcp.mobius.waila.api.IWailaRegistrar")
public class PluginWaila extends APlugin {

	@Optional.Method(modid = "Waila")
	public static void register(IWailaRegistrar registrar) {
		ProviderModular modular = new ProviderModular();
		registrar.registerBodyProvider(modular, TileModular.class);
		registrar.registerHeadProvider(modular, TileModular.class);
		registrar.registerTailProvider(modular, TileModular.class);
	}

	@Override
	public String getRequiredMod() {
		return "Waila";
	}

	@Override
	public void init() {
		FMLInterModComms.sendMessage("Waila", "register", PluginWaila.class.getName() + ".register");
	}

	@Override
	public boolean isActive() {
		return Config.pluginWaila;
	}
}
