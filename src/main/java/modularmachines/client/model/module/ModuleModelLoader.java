package modularmachines.client.model.module;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IModelComponent;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.model.IModuleModelState;

@SideOnly(Side.CLIENT)
public enum ModuleModelLoader {
	INSTANCE;
	
	@Nullable
	private static ImmutableMap<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> bakedModels;
	private static final Cache<Pair<ResourceLocation, IModuleModelState>, IBakedModel> cachedModels =
			CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
	private static Set<ResourceLocation> locations;
	
	@SuppressWarnings("unchecked")
	public void reloadModels() {
		List<ResourceLocation> modelLocations = new ArrayList<>();
		Map<ResourceLocation, Exception> loadingExceptions = Maps.newHashMap();
		Builder<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> modelBaker = new Builder<>();
		locations.forEach(location -> {
			Builder<VertexFormat, IBakedModel> baker = new Builder<>();
			IModel model = null;
			try {
				model = ModelLoaderRegistry.getModel(location);
			} catch (Exception e) {
				loadingExceptions.put(location, e);
			}
			if (model != null) {
				baker.put(DefaultVertexFormats.BLOCK, model.bake(model.getDefaultState(), DefaultVertexFormats.BLOCK, DefaultTextureGetter.INSTANCE));
				//baker.put(DefaultVertexFormats.ITEM, model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, DefaultTextureGetter.INSTANCE));
				modelBaker.put(location, baker.build());
				modelLocations.add(location);
			}
		});
		ModuleModelLoader.bakedModels = modelBaker.build();
		cachedModels.invalidateAll();
		loadingExceptions.values().forEach(Throwable::printStackTrace);
	}
	
	public void registerModels() {
		locations = GameRegistry.findRegistry(IModuleData.class).getValues().stream()
				.filter(Objects::nonNull)
				.map(data -> {
					IModelData modelData = data.getModel();
					if (modelData == null) {
						return Collections.<ResourceLocation>emptyList();
					}
					List<ResourceLocation> locations = new LinkedList<>(modelData.locations().values());
					if (data.getWallModelLocation() != null) {
						locations.add(data.getWallModelLocation());
					}
					return locations;
				})
				.flatMap(Collection::stream)
				.filter(Objects::nonNull).collect(Collectors.toSet());
	}
	
	public Set<ResourceLocation> getModelLocations() {
		return locations;
	}
	
	public enum DefaultTextureGetter implements Function<ResourceLocation, TextureAtlasSprite> {
		INSTANCE;
		
		@Override
		public TextureAtlasSprite apply(ResourceLocation location) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
		}
	}
	
	@Nullable
	public static IBakedModel getModel(IModule module, IModelState modelState, VertexFormat vertex, @Nullable BlockRenderLayer layer) {
		IModelComponent component = module.getComponent(IModelComponent.class);
		if (component == null) {
			return null;
		}
		IModuleModelState moduleModelState = component.getModelState();
		IBakedModel model = cachedModels.getIfPresent(Pair.of(module.getData().getRegistryName(), moduleModelState));
		IModelData data = getModelData(module);
		if (data == null || layer != null && !data.canRenderInLayer(module, layer)) {
			return null;
		}
		if (component.isModelNeedReload() || model == null) {
			ModelList modelList = new ModelList(module, data.locations(), vertex, modelState, DefaultTextureGetter.INSTANCE);
			component.setModelState(moduleModelState = data.createState(module));
			data.addModel(modelList, module, moduleModelState, layer);
			
			if (modelList.empty()) {
				if (module.isEmpty() || layer != BlockRenderLayer.CUTOUT) {
					return null;
				}
				model = modelList.missingModel();
			} else {
				model = BakedMultiModel.create(modelList.models());
			}
			if (data.cacheModel()) {
				cachedModels.put(Pair.of(module.getData().getRegistryName(), moduleModelState), model);
			}
			component.setModelNeedReload(false);
		}
		return model;
	}
	
	@Nullable
	private static IModelData getModelData(@Nullable IModule module) {
		if (module == null) {
			return null;
		}
		return module.getData().getModel();
	}
	
	@Nullable
	public static IBakedModel getModel(ResourceLocation location, VertexFormat format) {
		if (bakedModels == null || !bakedModels.containsKey(location)) {
			return null;
		}
		if (!bakedModels.get(location).containsKey(format)) {
			return null;
		}
		return bakedModels.get(location).get(format);
	}
}
