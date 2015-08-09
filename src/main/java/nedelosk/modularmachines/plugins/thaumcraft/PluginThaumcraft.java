package nedelosk.modularmachines.plugins.thaumcraft;

import nedelosk.nedeloskcore.plugins.Plugin;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.research.ResearchCategories;

public class PluginThaumcraft extends Plugin {

	@Override
	public void preInit() {
		ResearchCategories.registerCategory("MM", new ResourceLocation("thaumcraft", "textures/items/thaumonomiconcheat.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));
	}
	
	@Override
	public String getRequiredMod() {
		return "Thaumcraft";
	}
	
}
