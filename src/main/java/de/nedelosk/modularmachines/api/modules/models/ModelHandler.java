package de.nedelosk.modularmachines.api.modules.models;

import java.util.Locale;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ModelHandler<M extends IModule> implements IModelHandler<M> {

	public IBakedModel bakedModel;
	public boolean needReload;
	public String modelFolder;
	public IModuleContainer container;

	public ModelHandler(String modelFolder, IModuleContainer container) {
		this.modelFolder = modelFolder;
		this.container = container;
	}

	@Override
	public void setNeedReload(boolean needReload) {
		this.needReload = needReload;
	}

	@Override
	public boolean needReload() {
		return needReload;
	}

	@Override
	public IBakedModel getModel() {
		return bakedModel;
	}

	public ResourceLocation getModelLocation(){
		return getModelLocation(container, modelFolder);
	}

	public ResourceLocation getModelLocation(boolean status){
		return getModelLocation(container, modelFolder, status);
	}

	public ResourceLocation getModelLocation(String prefix){
		return getModelLocation(container, modelFolder, prefix);
	}

	public ResourceLocation getModelLocation(String prefix, boolean useSize){
		return getModelLocation(container, modelFolder, prefix, useSize);
	}

	public ResourceLocation getModelLocation(EnumModuleSize size){
		return getModelLocation(container, modelFolder, size);
	}

	public static ResourceLocation getModelLocation(IModuleContainer container, String modelFolder){
		return getModelLocation(container, modelFolder, null, true, false, false);
	}

	public static ResourceLocation getModelLocation(IModuleContainer container, String modelFolder, boolean status){
		return getModelLocation(container, modelFolder, null, true, true, status);
	}

	public static ResourceLocation getModelLocation(IModuleContainer container, String modelFolder, String prefix){
		return getModelLocation(container, modelFolder, prefix, false, false, false);
	}

	public static ResourceLocation getModelLocation(IModuleContainer container, String modelFolder, EnumModuleSize size){
		return getModelLocation(container, modelFolder, "", size != null, false, false);
	}

	public static ResourceLocation getModelLocation(IModuleContainer container, String modelFolder, String prefix, boolean useSize){
		return getModelLocation(container, modelFolder, prefix, useSize, false, false);
	}

	public static ResourceLocation getModelLocation(IModuleContainer container, String modelFolder, String prefix, boolean useSize, boolean useStatus, boolean status){
		String preFixNew = prefix;
		if(preFixNew == null){
			preFixNew = "";
		}
		if(useSize){
			if(!preFixNew.isEmpty()){
				preFixNew+="_";
			}
			preFixNew+= container.getModule().getSize(container).getName();
		}
		if(useStatus){
			preFixNew+= "_" + (status ? "on" : "off");
		}
		return new ResourceLocation(Loader.instance().activeModContainer().getModId(), "module/" + container.getMaterial().getName().toLowerCase(Locale.ENGLISH) + "/" + modelFolder + "/" + preFixNew);
	}

	public IBakedModel getBakedModel(ResourceLocation loc, IModelState modelState, VertexFormat format, Function bakedTextureGetter){
		IModel model = getModelOrDefault(loc, new ResourceLocation(loc.toString().replace(container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "default")));
		if(model != null){
			return model.bake(modelState, format, bakedTextureGetter);
		}
		return null;
	}

	public IBakedModel getBakedModel(ResourceLocation loc, ResourceLocation locDefault, IModelState modelState, VertexFormat format, Function bakedTextureGetter){
		IModel model = getModelOrDefault(loc, locDefault);
		if(model != null){
			return model.bake(modelState, format, bakedTextureGetter);
		}
		return null;
	}

	public IModel getModelOrDefault(ResourceLocation loc){
		return getModelOrDefault(loc, new ResourceLocation(loc.toString().replace(container.getMaterial().getName().toLowerCase(Locale.ENGLISH), "default")));
	}

	public static IModel getModelOrDefault(ResourceLocation loc, ResourceLocation locDefault){
		try{
			return ModelLoaderRegistry.getModel(loc);
		}catch(Exception e){
			try {
				return ModelLoaderRegistry.getModel(locDefault);
			} catch (Exception e1) {
				return null;
			}
		}
	}
}
