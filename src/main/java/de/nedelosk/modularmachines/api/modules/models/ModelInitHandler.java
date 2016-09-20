package de.nedelosk.modularmachines.api.modules.models;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelInitHandler implements IModelInitHandler {

	private final ResourceLocation[] locations;

	public ModelInitHandler(ResourceLocation... locations) {
		this.locations = locations;
	}

	@Override
	public void initModels(IModuleContainer container) {
		for(ResourceLocation location : locations){
			ModelLoaderRegistry.getModelOrMissing(location);
		}
	}
}
