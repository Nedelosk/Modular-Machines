package modularmachines.client.model.module;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModelLocations;
import modularmachines.api.modules.model.IModelProperty;
import modularmachines.client.model.TRSRBakedModel;

public class ModelList implements IModelList {
	private final List<IBakedModel> models = new LinkedList<>();
	private final VertexFormat format;
	private final IModelState modelState;
	private final IModelLocations cache;
	private final Function<ResourceLocation, TextureAtlasSprite> textureGetter;
	@Nullable
	private IBakedModel missingModel;
	
	public ModelList(IModelLocations cache, VertexFormat format, IModelState modelState, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
		this.cache = cache;
		this.format = format;
		this.modelState = modelState;
		this.textureGetter = textureGetter;
	}
	
	public void add(IModelProperty key) {
		add(cache.get(key));
	}
	
	public void add(@Nullable ResourceLocation location) {
		if (location == null) {
			return;
		}
		add(ModuleModelLoader.getModel(location, format));
	}
	
	public void add(@Nullable IBakedModel model) {
		if (model != null) {
			models.add(model);
		}
	}
	
	public void add(@Nullable IBakedModel model, float y) {
		if (model == null) {
			return;
		}
		add(model, m -> new TRSRBakedModel(m, 0F, y, 0F));
	}
	
	public void add(IModelProperty key, Function<IBakedModel, IBakedModel> modelWrapper) {
		add(cache.get(key), modelWrapper);
	}
	
	public void add(@Nullable ResourceLocation location, Function<IBakedModel, IBakedModel> modelWrapper) {
		if (location == null) {
			return;
		}
		IBakedModel bakedModel = ModuleModelLoader.getModel(location, format);
		if (bakedModel != null) {
			add(modelWrapper.apply(bakedModel));
		}
	}
	
	public void add(@Nullable IBakedModel model, Function<IBakedModel, IBakedModel> modelWrapper) {
		add(model == null ? null : modelWrapper.apply(model));
	}
	
	@Nullable
	@Override
	public IBakedModel get(@Nullable Module module) {
		if (module == null) {
			return null;
		}
		return ModuleModelLoader.getModel(module, modelState, format);
	}
	
	@Nullable
	@Override
	public IBakedModel get(@Nullable ResourceLocation location) {
		if (location == null) {
			return null;
		}
		return ModuleModelLoader.getModel(location, format);
	}
	
	@Nullable
	@Override
	public IBakedModel get(IModelProperty key) {
		return get(cache.get(key));
	}
	
	@Override
	public VertexFormat format() {
		return format;
	}
	
	@Override
	public boolean empty() {
		return models.isEmpty();
	}
	
	@Override
	public IModelState state() {
		return modelState;
	}
	
	@Override
	public Function<ResourceLocation, TextureAtlasSprite> textureGetter() {
		return textureGetter;
	}
	
	public List<IBakedModel> models() {
		return models;
	}
	
	@Override
	public IBakedModel missingModel() {
		if (missingModel == null) {
			missingModel = ModelLoaderRegistry.getMissingModel().bake(modelState, format, textureGetter);
		}
		return missingModel;
	}
}