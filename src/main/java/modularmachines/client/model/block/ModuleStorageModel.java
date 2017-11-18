package modularmachines.client.model.block;

import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class ModuleStorageModel implements IModel {
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		modularmachines.client.model.module.ModelLoader.INSTANCE.reloadModels();
		return new ModuleStorageModelBaked();
	}
}
