package modularmachines.common.plugins.theoneprobe;

import net.minecraftforge.fml.common.event.FMLInterModComms;

import modularmachines.common.config.Config;
import modularmachines.common.plugins.APlugin;

public class PluginTheOneProbe extends APlugin {

	@Override
	public String getRequiredMod() {
		return "theoneprobe";
	}

	@Override
	public void init() {
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "modularmachines.common.plugins.theoneprobe.DefaultInfoProvider");
	}

	@Override
	public boolean isActive() {
		return Config.pluginTheOneProbe;
	}
}
