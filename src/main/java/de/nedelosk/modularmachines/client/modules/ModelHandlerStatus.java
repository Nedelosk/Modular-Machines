package de.nedelosk.modularmachines.client.modules;

import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class ModelHandlerStatus extends ModelHandler implements IModelInitHandler {

	public boolean status;
	protected ResourceLocation[] locations;

	public ModelHandlerStatus(ResourceLocation[] locations) {
		this.locations = locations;
	}

	@Override
	public void reload(IModuleState state, IModelState modelState, VertexFormat format, Function bakedTextureGetter, List otherHandlers) {
		if(status){
			bakedModel = ModelLoaderRegistry.getModelOrMissing(locations[0]).bake(modelState, format, bakedTextureGetter);
		}else{
			bakedModel = ModelLoaderRegistry.getModelOrMissing(locations[1]).bake(modelState, format, bakedTextureGetter);
		}

	}

	@Override
	public void initModels(IModuleContainer container) {
		ModelLoaderRegistry.getModelOrMissing(locations[0]);
		ModelLoaderRegistry.getModelOrMissing(locations[1]);
	}
}
