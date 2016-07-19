package de.nedelosk.modularmachines.client.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.client.model.ModelModular;
import de.nedelosk.modularmachines.client.model.TRSRBakedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class ModelHandlerDrawer extends ModelHandler<IModuleModuleStorage> implements IModelHandler<IModuleModuleStorage>, IModelInitHandler{

	/**
	 * 0 = stick_down
	 * 1 = stick_up
	 * 2 = small_down
	 * 3 = small_middle
	 * 4 = small_up
	 * 5 = middle_middle
	 * 6 = middle_up
	 * 7 = large
	 */
	private final ResourceLocation[] frontWalls;
	private final ResourceLocation drawer;
	private final ResourceLocation wall;

	public ModelHandlerDrawer(ResourceLocation drawer, ResourceLocation wall, ResourceLocation[] frontWalls) {
		this.drawer = drawer;
		this.wall = wall;
		this.frontWalls = frontWalls;
	}

	@Override
	public void reload(IModuleState<IModuleModuleStorage> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, List<IModelHandler> otherHandlers) {
		List<IBakedModel> models = new ArrayList<>();
		models.add(ModelLoaderRegistry.getModelOrMissing(drawer).bake(modelState, format, bakedTextureGetter));

		EnumModuleSize size = null;
		String type = state.getModule().getCurrentPosition(state);
		List<IModuleState<? extends IModuleStoraged>> modules = new ArrayList<>();
		for(IModuleState<? extends IModuleStoraged> stateStoraged : state.getModular().getModules(IModuleStoraged.class)){
			if(stateStoraged != null && stateStoraged.getModule().getCurrentPosition(state).equals(type)){
				modules.add(stateStoraged);
			}
		}
		for(IModuleState<? extends IModuleStoraged> storagedState : modules){
			size = EnumModuleSize.getNewSize(size, storagedState.getModule().getSize());
			if(size == EnumModuleSize.MIDDLE){
				models.add(ModelLoaderRegistry.getModelOrMissing(wall).bake(modelState, format, bakedTextureGetter));
			}else if(size == EnumModuleSize.SMALL){
				models.add(new TRSRBakedModel(ModelLoaderRegistry.getModelOrMissing(wall).bake(modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
			}
		}

		for(IBakedModel model : getWallModels(state, modelState, format, bakedTextureGetter)){
			models.add(model);
		}
		if(type.equals(EnumPosition.TOOL.getName())){
			bakedModel = new TRSRBakedModel(new ModelModular.ModularBaked(models), 0F, 0F, 0F, 0F, (float) (Math.PI), 0F, 1F);
		}else if(type.equals(EnumPosition.DRIVE.getName())){
			bakedModel = new ModelModular.ModularBaked(models);
		}
	}

	public List<IBakedModel> getWallModels(IModuleState<IModuleModuleStorage> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter){
		List<IBakedModel> wallModels = new ArrayList<>();
		IModuleModuleStorage drawerModule = state.getModule();
		EnumModuleSize size = null;
		String type = state.getModule().getCurrentPosition(state);
		List<IModuleState<? extends IModuleStoraged>> modules = new ArrayList<>();
		for(IModuleState<? extends IModuleStoraged> stateStoraged : state.getModular().getModules(IModuleStoraged.class)){
			if(stateStoraged != null && stateStoraged.getModule().getCurrentPosition(state).equals(type)){
				modules.add(stateStoraged);
			}
		}
		for(IModuleState<? extends IModuleStoraged> stateStoraged : modules){
			EnumModuleSize moduleSize = stateStoraged.getModule().getSize();
			IModuleStoraged module = stateStoraged.getModule();
			size = EnumModuleSize.getNewSize(size, moduleSize);
			EnumWallType wallType = stateStoraged.getModule().getWallType(stateStoraged);
			ResourceLocation loc = null;
			if(size == EnumModuleSize.SMALL){
				if(wallType != EnumWallType.NONE){
					if(wallType == EnumWallType.WINDOW){
						wallModels.add(new TRSRBakedModel(ModelLoaderRegistry.getModelOrMissing(module.getWindowLocation(stateStoraged.getContainer())).bake(modelState, format, bakedTextureGetter), 0F, 0.5F, 0F, 1F));
					}else{
						loc = frontWalls[4];
					}
				}
				wallModels.add(ModelLoaderRegistry.getModelOrMissing(frontWalls[1]).bake(modelState, format, bakedTextureGetter));
			}else if(size == EnumModuleSize.MIDDLE){
				if(wallType != EnumWallType.NONE){
					if(moduleSize == EnumModuleSize.SMALL){
						if(wallType == EnumWallType.WINDOW){
							wallModels.add(new TRSRBakedModel(ModelLoaderRegistry.getModelOrMissing(module.getWindowLocation(stateStoraged.getContainer())).bake(modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
						}else{
							loc = frontWalls[3];
						}
					}else if(moduleSize == EnumModuleSize.MIDDLE){	
						if(wallType == EnumWallType.WINDOW){
							wallModels.add(new TRSRBakedModel(ModelLoaderRegistry.getModelOrMissing(module.getWindowLocation(stateStoraged.getContainer())).bake(modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
						}else{
							loc = frontWalls[6];
						}
					}
				}
				wallModels.add(ModelLoaderRegistry.getModelOrMissing(frontWalls[0]).bake(modelState, format, bakedTextureGetter));
			}else if(size == EnumModuleSize.LARGE){
				if(wallType != EnumWallType.NONE){
					if(moduleSize == EnumModuleSize.SMALL){
						if(wallType == EnumWallType.WINDOW){
							loc = module.getWindowLocation(stateStoraged.getContainer());
						}else{
							loc = frontWalls[2];
						}
					}else if(moduleSize == EnumModuleSize.MIDDLE){	
						if(wallType == EnumWallType.WINDOW){
							wallModels.add(ModelLoaderRegistry.getModelOrMissing(module.getWindowLocation(stateStoraged.getContainer())).bake(modelState, format, bakedTextureGetter));
						}else{
							loc = frontWalls[5];
						}
					}else if(moduleSize == EnumModuleSize.LARGE){
						if(wallType == EnumWallType.WINDOW){
							wallModels.add(ModelLoaderRegistry.getModelOrMissing(module.getWindowLocation(stateStoraged.getContainer())).bake(modelState, format, bakedTextureGetter));
						}else{
							loc = frontWalls[7];
						}
					}
				}
			}
			if(loc != null){
				wallModels.add(ModelLoaderRegistry.getModelOrMissing(loc).bake(modelState, format, bakedTextureGetter));
			}
		}
		if(size == EnumModuleSize.SMALL){
			wallModels.add(ModelLoaderRegistry.getModelOrMissing(frontWalls[5]).bake(modelState, format, bakedTextureGetter));
		}else if(size == EnumModuleSize.MIDDLE){
			wallModels.add(ModelLoaderRegistry.getModelOrMissing(frontWalls[2]).bake(modelState, format, bakedTextureGetter));
		}
		return wallModels;
	}

	@Override
	public void initModels(IModuleContainer container) {
		ModelLoaderRegistry.getModelOrMissing(drawer);
		ModelLoaderRegistry.getModelOrMissing(wall);
		for(ResourceLocation loc : frontWalls){
			ModelLoaderRegistry.getModelOrMissing(loc);
		}
	}

}
