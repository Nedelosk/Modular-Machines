package de.nedelosk.modularmachines.api.modules.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.client.model.animation.Animation;
import net.minecraftforge.common.animation.Event;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModuleModelLoader {

	private static ImmutableMap<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> models;

	public static void loadModels() {
		if(models != null){
			models = null;
		}
		IModel missingModel = ModelLoaderRegistry.getMissingModel();
		List<ResourceLocation> modelLocations = new ArrayList<>();
		Map<ResourceLocation, Exception> loadingExceptions = Maps.newHashMap();
		Builder<ResourceLocation, ImmutableMap<VertexFormat, IBakedModel>> modelBaker = new Builder<>();
		for(IModuleItemContainer itemContainer : ModuleManager.MODULE_CONTAINERS){
			if(itemContainer != null){
				for(IModuleContainer moduleContainer : itemContainer.getContainers()){
					IModule module = moduleContainer.getModule();
					Map<ResourceLocation, ResourceLocation> locatons = module.getModelLocations(itemContainer);
					if(locatons != null && !locatons.isEmpty()){
						for(Entry<ResourceLocation, ResourceLocation> locaton : locatons.entrySet()){
							if(locaton != null && !modelLocations.contains(locaton.getKey())){
								Builder<VertexFormat, IBakedModel> baker = new Builder<>();
								IModel model;
								try{
									model= ModelLoaderRegistry.getModel(locaton.getKey());
								}catch(Exception exceptionModel){
									loadingExceptions.put(locaton.getKey(), exceptionModel);
									try {
										model = ModelLoaderRegistry.getModel(locaton.getValue());
									} catch (Exception exceptionDefaultModel) {
										loadingExceptions.put(locaton.getValue(), exceptionDefaultModel);
										model = null;
									}
								}
								if(model != null){
									baker.put(DefaultVertexFormats.BLOCK, model.bake(model.getDefaultState(), DefaultVertexFormats.BLOCK, DefaultTextureGetter.INSTANCE));
									baker.put(DefaultVertexFormats.ITEM, model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, DefaultTextureGetter.INSTANCE));
									modelBaker.put(locaton.getKey(), baker.build());
									modelLocations.add(locaton.getKey());
								}
							}
						}
					}
					ResourceLocation windowLocation = module.getWindowLocation(itemContainer);
					if(windowLocation != null && !modelLocations.contains(windowLocation)){
						Builder<VertexFormat, IBakedModel> windowBaker = new Builder<>();
						IModel model;
						try{
							model = ModelLoaderRegistry.getModel(windowLocation);
						}catch(Exception exceptionModel){
							loadingExceptions.put(windowLocation, exceptionModel);
							model = null;
						}
						if(model != null){
							windowBaker.put(DefaultVertexFormats.BLOCK, model.bake(model.getDefaultState(), DefaultVertexFormats.BLOCK, DefaultTextureGetter.INSTANCE));
							windowBaker.put(DefaultVertexFormats.ITEM, model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, DefaultTextureGetter.INSTANCE));
							modelBaker.put(windowLocation, windowBaker.build());
							modelLocations.add(windowLocation);
						}
					}
				}
			}
		}
		ModuleModelLoader.models = modelBaker.build();
		for(Exception e : loadingExceptions.values()){
			e.printStackTrace();
		}
	}

	public static enum DefaultTextureGetter implements Function<ResourceLocation, TextureAtlasSprite>{
		INSTANCE;

		@Override
		public TextureAtlasSprite apply(ResourceLocation location)
		{
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
		}
	} 

	public static IBakedModel getModel(IModuleState moduleState, IStorage storage, IModelState modelState, VertexFormat vertex){
		IModelHandler modelHandler = ((IModuleStateClient)moduleState).getModelHandler();

		if(modelHandler != null){
			IBakedModel model = modelHandler.getModel();
			if(modelHandler.needReload() || model == null){
				if(modelHandler instanceof IModelHandlerAnimated){
					IModelHandlerAnimated modelHandlerAnimated = (IModelHandlerAnimated) modelHandler;
					Minecraft mc = Minecraft.getMinecraft();
					float time = Animation.getWorldTime(mc.theWorld, mc.getRenderPartialTicks());
					Pair<IModelState, Iterable<Event>> pair = modelHandlerAnimated.getStateMachine(moduleState).apply(time);

					((IModelHandlerAnimated)modelHandler).handleEvents(modelHandler, time, pair.getRight());
					modelHandler.reload(moduleState, storage, new ModelStateComposition(modelState, pair.getLeft()), vertex, DefaultTextureGetter.INSTANCE);
					model = modelHandler.getModel();
				}else{
					modelHandler.reload(moduleState, storage, modelState, vertex, DefaultTextureGetter.INSTANCE);
					model = modelHandler.getModel();
				}
				modelHandler.setNeedReload(false);
			}
			if(model != null){
				return model;
			}
		}
		return null;
	}

	public static IBakedModel getModel(ResourceLocation location, VertexFormat format){
		if(!models.containsKey(location)){
			return null;
		}
		if(!models.get(location).containsKey(format)){
			return null;
		}
		return models.get(location).get(format);
	}

	/* LOACTIONS */
	public static ResourceLocation getModelLocation(String modID, String material, String folder){
		return getModelLocation(modID, material, folder, null, null, null);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, String prefix){
		return getModelLocation(modID, material, folder, prefix, null, null);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, EnumModuleSizes size){
		return getModelLocation(modID, material, folder, null, size, null);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, Boolean status){
		return getModelLocation(modID, material, folder, null, null, status);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, EnumModuleSizes size, Boolean status){
		return getModelLocation(modID, material, folder, null, size, status);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, String prefix, Boolean status){
		return getModelLocation(modID, material, folder, prefix, null, status);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, String prefix, EnumModuleSizes size){
		return getModelLocation(modID, material, folder, prefix, size, null);
	}

	public static ResourceLocation getModelLocation(String modID, String material, String folder, String prefix, EnumModuleSizes size, Boolean status){
		String preFixNew = prefix;
		if(preFixNew == null){
			preFixNew = "";
		}
		if(size != null){
			if(!preFixNew.isEmpty()){
				preFixNew +="_";
			}
			preFixNew += size.getName();
		}
		if(status != null){
			preFixNew += (!preFixNew.isEmpty() ? "_" : "") + (status ? "on" : "off");
		}
		if(preFixNew.isEmpty()){
			preFixNew = "default";
		}
		return new ResourceLocation(Loader.instance().activeModContainer().getModId(), "module/" + material.toLowerCase(Locale.ENGLISH) + "/" + folder + "/" + preFixNew);
	}
}
