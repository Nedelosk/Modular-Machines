package modularmachines.client.model.module;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.common.ModularMachines;
import modularmachines.common.modules.logic.ModelComponent;
import modularmachines.common.utils.ModuleUtil;

@SideOnly(Side.CLIENT)
public class ModelLoader {

	private static ImmutableMap<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> models;

	@SuppressWarnings("unchecked")
	public static void loadModels() {
		IModel missingModel = ModelLoaderRegistry.getMissingModel();
		List<ResourceLocation> modelLocations = new ArrayList<>();
		Map<ResourceLocation, Exception> loadingExceptions = Maps.newHashMap();
		Builder<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> modelBaker = new Builder<>();
		ModularMachines.dataRegistry.getValues().stream()
				.filter(Objects::nonNull)
				.map(d-> {
					IModelData<IBakedModel> modelData = d.getModel(TileEntity.class);
					if(modelData == null){
						return Collections.<ResourceLocation>emptyList();
					}
					return modelData.getValidLocations();
				})
				.flatMap(Collection::stream)
				.filter(l -> Objects.nonNull(l) && !modelLocations.contains(l))
				.forEach(location -> {
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
					/*ResourceLocation windowLocation = module.getWindowLocation(data);
					if (windowLocation != null && !modelLocations.contains(windowLocation)) {
						Builder<VertexFormat, IBakedModel> windowBaker = new Builder<>();
						IModel model;
						try {
							model = ModelLoaderRegistry.getModel(windowLocation);
						} catch (Exception exceptionModel) {
							loadingExceptions.put(windowLocation, exceptionModel);
							model = null;
						}
						if (model != null) {
							windowBaker.put(DefaultVertexFormats.BLOCK, model.bake(model.getDefaultState(), DefaultVertexFormats.BLOCK, DefaultTextureGetter.INSTANCE));
							windowBaker.put(DefaultVertexFormats.ITEM, model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, DefaultTextureGetter.INSTANCE));
							modelBaker.put(windowLocation, windowBaker.build());
							modelLocations.add(windowLocation);
						}
					}*/
				});
		ModelLoader.models = modelBaker.build();
		loadingExceptions.values().forEach(Throwable::printStackTrace);
	}

	public static enum DefaultTextureGetter implements Function<ResourceLocation, TextureAtlasSprite> {
		INSTANCE;

		@Override
		public TextureAtlasSprite apply(ResourceLocation location) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
		}
	}

	@Nullable
	public static IBakedModel getModel(Module module, IStorage storage, IModelState modelState, VertexFormat vertex) {
		ModelComponent component = ModuleUtil.getModel(module.getContainer());
		if(component == null){
			return null;
		}
		IBakedModel model = component.getModel(module.getIndex());
		IModelData<IBakedModel> data = getModelData(module);
		if(data == null){
			return null;
		}
		if (module.isModelNeedReload() || model == null) {
			//component.setModel(module.getIndex(), model = data.getModel(module, storage, modelState, vertex, DefaultTextureGetter.INSTANCE));
			module.setModelNeedReload(false);
		}
		if (model != null) {
			return model;
		}
		return null;
	}
	
	@Nullable
	public static IBakedModel getModel(Module module, IModelState modelState, VertexFormat vertex) {
		IModuleContainer container = module.getContainer();
		if(container == null){
			return null;
		}
		ModelComponent component = ModuleUtil.getModel(container);
		if(component == null){
			return null;
		}
		IBakedModel model = component.getModel(module.getIndex());
		IModelData<IBakedModel> data = getModelData(module);
		if(data == null){
			return null;
		}
		if (module.isModelNeedReload() || model == null) {
			component.setModel(module.getIndex(), model = data.getModel(module, modelState, vertex, DefaultTextureGetter.INSTANCE));
			module.setModelNeedReload(false);
		}
		return model;
	}
	
	@Nullable
	private static IModelData<IBakedModel> getModelData(@Nullable Module module){
		if(module == null){
			return null;
		}
		return module.getData().getModel(TileEntity.class);
	}

	@Nullable
	public static IBakedModel getModel(ResourceLocation location, VertexFormat format) {
		if (!models.containsKey(location)) {
			return null;
		}
		if (!models.get(location).containsKey(format)) {
			return null;
		}
		return models.get(location).get(format);
	}
}
