package de.nedelosk.modularmachines.client.modules;

import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class ModelHandlerDefault extends ModelHandler implements IModelHandler, IModelInitHandler {

	private final ResourceLocation location;

	public ModelHandlerDefault(ResourceLocation location) {
		this.location = location;
	}

	@Override
	public void initModels(IModuleContainer container) {
		ModelLoaderRegistry.getModelOrMissing(location);
	}

	@Override
	public void reload(IModuleState state, IModelState modelState, VertexFormat format, Function bakedTextureGetter, List otherHandlers) {
		bakedModel = ModelLoaderRegistry.getModelOrMissing(location).bake(modelState, format, bakedTextureGetter);
	}
}
