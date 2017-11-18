package modularmachines.api.modules.model;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.model.IModelState;

import modularmachines.api.modules.Module;

public interface IModelList {
	void add(IModelKey key);
	
	void add(IModelKey key, Function<IBakedModel, IBakedModel> modelWrapper);
	
	void add(@Nullable ResourceLocation location);
	
	void add(@Nullable ResourceLocation location, Function<IBakedModel, IBakedModel> modelWrapper);
	
	void add(@Nullable IBakedModel model);
	
	void add(@Nullable IBakedModel model, float y);
	
	void add(@Nullable IBakedModel model, Function<IBakedModel, IBakedModel> modelWrapper);
	
	@Nullable
	IBakedModel get(@Nullable Module module);
	
	@Nullable
	IBakedModel get(@Nullable ResourceLocation location);
	
	@Nullable
	IBakedModel get(IModelKey key);
	
	boolean empty();
	
	VertexFormat format();
	
	IModelState state();
	
	Function<ResourceLocation, TextureAtlasSprite> textureGetter();
	
	List<IBakedModel> models();
	
	IBakedModel missingModel();
}
