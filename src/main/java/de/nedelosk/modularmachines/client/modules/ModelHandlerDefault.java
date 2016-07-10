package de.nedelosk.modularmachines.client.modules;

import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class ModelHandlerDefault implements IModuleModelHandler<IModuleHeater> {

	private final ResourceLocation location;

	public ModelHandlerDefault(ResourceLocation location) {
		this.location = location;
	}

	@Override
	public void initModels(IModuleContainer container) {
		ModelLoaderRegistry.getModelOrMissing(location);
	}

	@Override
	public IBakedModel getModel(IModuleState<IModuleHeater> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, List<IModuleModelHandler> otherHandlers) {
		return ModelLoaderRegistry.getModelOrMissing(location).bake(modelState, format, bakedTextureGetter);
	}
}
