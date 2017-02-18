package modularmachines.client.model.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

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

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.modules.logic.ModelComponent;
import modularmachines.common.utils.ModuleUtil;

@SideOnly(Side.CLIENT)
public class ModelLoader {

	private static ImmutableMap<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> models;

	public static void loadModels() {
		if (models != null) {
			models = null;
		}
		IModel missingModel = ModelLoaderRegistry.getMissingModel();
		List<ResourceLocation> modelLocations = new ArrayList<>();
		Map<ResourceLocation, Exception> loadingExceptions = Maps.newHashMap();
		Builder<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> modelBaker = new Builder<>();
		for (ModuleData data : ModularMachines.DATAS) {
			if (data != null) {
				IModelData<IBakedModel> modelData = data.getModel(TileEntity.class);
				Collection<ResourceLocation> locations = modelData.getValidLocations();
				if (locations != null && !locations.isEmpty()) {
					for (ResourceLocation locaton : locations) {
						if (locaton != null && !modelLocations.contains(locaton)) {
							Builder<VertexFormat, IBakedModel> baker = new Builder<>();
							IModel model = null;
							try {
								model = ModelLoaderRegistry.getModel(locaton);
							} catch (Exception e) {
								loadingExceptions.put(locaton, e);
							}
							if (model != null) {
								baker.put(DefaultVertexFormats.BLOCK, model.bake(model.getDefaultState(), DefaultVertexFormats.BLOCK, DefaultTextureGetter.INSTANCE));
								baker.put(DefaultVertexFormats.ITEM, model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, DefaultTextureGetter.INSTANCE));
								modelBaker.put(locaton, baker.build());
								modelLocations.add(locaton);
							}
						}
					}
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
			}
		}
		ModelLoader.models = modelBaker.build();
		for (Exception e : loadingExceptions.values()) {
			e.printStackTrace();
		}
	}

	public static enum DefaultTextureGetter implements Function<ResourceLocation, TextureAtlasSprite> {
		INSTANCE;

		@Override
		public TextureAtlasSprite apply(ResourceLocation location) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
		}
	}

	public static IBakedModel getModel(Module module, IStorage storage, IModelState modelState, VertexFormat vertex) {
		ModelComponent component = ModuleUtil.getModel(module.getLogic());
		IBakedModel model = component.getModel(module.getIndex());
		IModelData<IBakedModel> data = getModelData(module);
		if (module.isModelNeedReload() || model == null) {
			component.setModel(module.getIndex(), model = data.getModel(module, storage, modelState, vertex, DefaultTextureGetter.INSTANCE));
			module.setModelNeedReload(false);
		}
		if (model != null) {
			return model;
		}
		return null;
	}
	
	private static IModelData<IBakedModel> getModelData(Module module){
		if(module == null){
			return null;
		}
		return module.getData().getModel(TileEntity.class);
	}

	public static IBakedModel getModel(ResourceLocation location, VertexFormat format) {
		if (!models.containsKey(location)) {
			return null;
		}
		if (!models.get(location).containsKey(format)) {
			return null;
		}
		return models.get(location).get(format);
	}

	/* LOACTIONS */
	public static ResourceLocation getModelLocation(String modID, String material, String folder) {
		return getModelLocation(modID, material, folder, null, null, null);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, String prefix) {
		return getModelLocation(modID, material, folder, prefix, null, null);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, EnumModuleSizes size) {
		return getModelLocation(modID, material, folder, null, size, null);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, Boolean status) {
		return getModelLocation(modID, material, folder, null, null, status);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, EnumModuleSizes size, Boolean status) {
		return getModelLocation(modID, material, folder, null, size, status);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, String prefix, Boolean status) {
		return getModelLocation(modID, material, folder, prefix, null, status);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, String prefix, EnumModuleSizes size) {
		return getModelLocation(modID, material, folder, prefix, size, null);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, String prefix, EnumModuleSizes size, Boolean status) {
		String preFixNew = prefix;
		if (preFixNew == null) {
			preFixNew = "";
		}
		if (size != null) {
			if (!preFixNew.isEmpty()) {
				preFixNew += "_";
			}
			preFixNew += size.getName();
		}
		if (status != null) {
			preFixNew += (!preFixNew.isEmpty() ? "_" : "") + (status ? "on" : "off");
		}
		if (preFixNew.isEmpty()) {
			preFixNew = "default";
		}
		return new ResourceLocation(modID, "module/" + material.toLowerCase(Locale.ENGLISH) + "/" + folder + "/" + preFixNew);
	}
}
