package de.nedelosk.modularmachines.client.modules;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleDrive;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleEngine;
import de.nedelosk.modularmachines.client.model.TRSRBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class ModelHandlerEngine extends ModelHandler<IModuleEngine> implements IModelHandler<IModuleEngine>, IModelInitHandler {

	public final ResourceLocation engine;

	public ModelHandlerEngine(IModuleContainer container) {
		super("engines", container);
		this.engine = getModelLocation(container.getModule().getSize());
	}

	@Override
	public void reload(IModuleState<IModuleEngine> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		EnumModuleSize size = null;
		for(IModuleState<IModuleDrive> drive : state.getModular().getModules(IModuleDrive.class)){
			if(drive.getIndex() == state.getIndex()){
				break;
			}else{
				size = EnumModuleSize.getNewSize(size, drive.getModule().getSize());
			}
		}
		if(size == null){
			bakedModel = new TRSRBakedModel(getBakedModel(engine, modelState, format, bakedTextureGetter), 0F, 0.5F, 0F, 1F);
		}else if(size == EnumModuleSize.SMALL){
			bakedModel = new TRSRBakedModel(getBakedModel(engine, modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F);
		}else{
			bakedModel = getBakedModel(engine, modelState, format, bakedTextureGetter);
		}
	}

	@Override
	public void initModels(IModuleContainer container) {
		getModelOrDefault(engine);
	}
}
