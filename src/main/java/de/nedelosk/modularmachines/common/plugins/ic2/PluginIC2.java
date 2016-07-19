package de.nedelosk.modularmachines.common.plugins.ic2;

import de.nedelosk.modularmachines.common.core.ItemManager;
import de.nedelosk.modularmachines.common.items.ItemModuleMeta;
import de.nedelosk.modularmachines.common.plugins.APlugin;

public class PluginIC2 extends APlugin {

	@Override
	public void preInit() {
		ItemManager.itemEngineEU = ItemManager.register(new ItemModuleMeta("engineEU", new String[] { "iron", "bronze", "steel", "magmarium" }));
	}

	@Override
	public String getRequiredMod() {
		return "IC2";
	}
}
