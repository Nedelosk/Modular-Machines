package modularmachines.client.model.module;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
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

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.components.IModelComponent;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModuleKeyGenerator;
import modularmachines.api.modules.model.IModuleModelBakery;
import modularmachines.client.model.ModuleModelRegistry;

@SideOnly(Side.CLIENT)
public enum ModuleModelLoader {
	INSTANCE;
	
	@Nullable
	private static ImmutableMap<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> bakedModels;
	private static final Cache<Pair<String, BlockRenderLayer>, IBakedModel> cachedModels =
			CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
	private static Set<ResourceLocation> locations = Collections.emptySet();
	
	@SuppressWarnings("unchecked")
	public void reloadModels() {
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
				modelBaker.put(location, baker.build());
			}
		});
		ModuleModelLoader.bakedModels = modelBaker.build();
		cachedModels.invalidateAll();
		loadingExceptions.values().forEach(Throwable::printStackTrace);
	}
	
	public void registerModels() {
		locations = GameRegistry.findRegistry(IModuleData.class).getValues().stream()
				.map(ModuleModelRegistry.INSTANCE::getModel)
				.filter(Objects::nonNull)
				.map(IModuleModelBakery::getDependencies)
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
	public static IBakedModel getModel(IModule module, IModelInfo modelInfo) {
		if (module.isEmpty()) {
			return null;
		}
		IModelComponent component = module.getComponent(IModelComponent.class);
		if (component == null) {
			return null;
		}
		IModuleData data = module.getData();
		String modelKey = component.getModelKey();
		IBakedModel model = modelKey == null ? null : cachedModels.getIfPresent(Pair.of(modelKey, modelInfo.getLayer()));
		IModuleModelBakery bakery = ModuleModelRegistry.INSTANCE.getModel(module.getData());
		BlockRenderLayer layer = modelInfo.getLayer();
		if (bakery == null || layer != null && !bakery.canRenderInLayer(module, layer)) {
			return null;
		}
		if (component.isModelNeedReload() || model == null) {
			ModelList modelList = new ModelList(modelInfo);
			IModuleKeyGenerator generator = ModuleModelRegistry.INSTANCE.getGenerator(data);
			component.setModelKey(modelKey = generator.generateKey(module));
			bakery.bakeModel(module, modelInfo, modelList);
			
			if (modelList.empty()) {
				if (module.isEmpty() || layer != BlockRenderLayer.CUTOUT) {
					return null;
				}
				model = modelInfo.bakeMissingModel(module);
			} else {
				model = BakedMultiModel.create(modelList.models());
			}
			cachedModels.put(Pair.of(modelKey, modelInfo.getLayer()), model);
			component.setModelNeedReload(false);
		}
		return model;
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
