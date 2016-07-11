package de.nedelosk.modularmachines.client.modules;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;

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
