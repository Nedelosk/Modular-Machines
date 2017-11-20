package modularmachines.client.model.block;

import java.util.Collection;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import modularmachines.client.model.module.ModuleModelLoader;

public class ModuleContainerModel implements IModel {
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		ModuleModelLoader.INSTANCE.reloadModels();
		return new ModuleContainerModelBaked();
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ModuleModelLoader.INSTANCE.getModelLocations();
	}
}
