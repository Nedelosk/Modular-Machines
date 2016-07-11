package de.nedelosk.modularmachines.client.modules;

import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleDrive;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.client.model.TRSRBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class ModelHandlerEngine extends ModelHandler<IModuleEngine> implements IModelHandler<IModuleEngine>, IModelInitHandler {

	public final ResourceLocation engine;

	public ModelHandlerEngine(ResourceLocation engine) {
		this.engine = engine;
	}

	@Override
	public void reload(IModuleState<IModuleEngine> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, List<IModelHandler> otherHandlers) {
		EnumModuleSize position = null;
		for(IModuleState<IModuleDrive> drive : state.getModular().getModules(IModuleDrive.class)){
			if(drive.getIndex() == state.getIndex()){
				break;
			}else{
				position = EnumModuleSize.getNewSize(position, drive.getModule().getSize());
			}
		}
		if(position == null){
			bakedModel = new TRSRBakedModel(ModelLoaderRegistry.getModelOrMissing(engine).bake(modelState, format, bakedTextureGetter), 0F, 0.5F, 0F, 1F);
		}else if(position == EnumModuleSize.SMALL){
			bakedModel = new TRSRBakedModel(ModelLoaderRegistry.getModelOrMissing(engine).bake(modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F);
		}else{
			bakedModel = ModelLoaderRegistry.getModelOrMissing(engine).bake(modelState, format, bakedTextureGetter);
		}
	}

	@Override
	public void initModels(IModuleContainer container) {
		ModelLoaderRegistry.getModelOrMissing(engine);
	}
}
