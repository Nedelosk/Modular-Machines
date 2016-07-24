package de.nedelosk.modularmachines.client.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
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
	private final ResourceLocation[] walls;
	private final ResourceLocation drawer;
	private final ResourceLocation top;
	private final ResourceLocation back;
	private final ResourceLocation wall;

	public ModelHandlerDrawer(IModuleContainer container) {
		super("module_storage", container);
		drawer = getModelLocation("drawer");
		top = getModelLocation("top");
		back = getModelLocation("back");
		wall = getModelLocation("wall");
		walls = new ResourceLocation[]{ 
				getModelLocation("front_walls/stick_down"),
				getModelLocation("front_walls/stick_up"),
				getModelLocation("front_walls/small_down"),
				getModelLocation("front_walls/small_middle"),
				getModelLocation("front_walls/small_up"),
				getModelLocation("front_walls/middle_up"),
				getModelLocation("front_walls/middle_middle"),
				getModelLocation("front_walls/large")
		};
	}

	@Override
	public void reload(IModuleState<IModuleModuleStorage> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		EnumPosition type = state.getModule().getCurrentPosition(state);
		if(type != EnumPosition.BACK && type != EnumPosition.TOP){
			List<IBakedModel> models = new ArrayList<>();
			models.add(getBakedModel(drawer, modelState, format, bakedTextureGetter));

			EnumModuleSize size = null;
			List<IModuleState> modules = new ArrayList<>();
			for(IModuleState stateStoraged : state.getModular().getModuleStorage(type).getModules()){
				if(stateStoraged != null){
					modules.add(stateStoraged);
				}
			}
			for(IModuleState storagedState : modules){
				size = EnumModuleSize.getNewSize(size, storagedState.getModule().getSize());
				if(size == EnumModuleSize.MIDDLE){
					models.add(getBakedModel(wall, modelState, format, bakedTextureGetter));
				}else if(size == EnumModuleSize.SMALL){
					models.add(new TRSRBakedModel(getBakedModel(wall, modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
				}
			}

			for(IBakedModel model : getWallModels(state, modelState, format, bakedTextureGetter)){
				models.add(model);
			}
			if(type.equals(EnumPosition.LEFT)){
				bakedModel = new TRSRBakedModel(new ModelModular.ModularBaked(models), 0F, 0F, 0F, 0F, (float) (Math.PI), 0F, 1F);
			}else if(type.equals(EnumPosition.RIGHT)){
				bakedModel = new ModelModular.ModularBaked(models);
			}
		}else if(type == EnumPosition.TOP){
			bakedModel = getBakedModel(top, modelState, format, bakedTextureGetter);
		}else if(type == EnumPosition.BACK){
			bakedModel = getBakedModel(back, modelState, format, bakedTextureGetter);
		}
	}

	public List<IBakedModel> getWallModels(IModuleState<IModuleModuleStorage> state, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter){
		List<IBakedModel> wallModels = new ArrayList<>();
		IModuleModuleStorage drawerModule = state.getModule();
		EnumModuleSize size = null;
		EnumPosition type = state.getModule().getCurrentPosition(state);
		List<IModuleState> modules = new ArrayList<>();
		for(IModuleState stateStoraged : state.getModular().getModuleStorage(type).getModules()){
			if(stateStoraged != null){
				modules.add(stateStoraged);
			}
		}
		for(IModuleState stateStoraged : modules){
			EnumModuleSize moduleSize = stateStoraged.getModule().getSize();
			IModule module = stateStoraged.getModule();
			size = EnumModuleSize.getNewSize(size, moduleSize);
			EnumWallType wallType = stateStoraged.getModule().getWallType(stateStoraged);
			ResourceLocation loc = null;
			if(size == EnumModuleSize.SMALL){
				if(wallType != EnumWallType.NONE){
					if(wallType == EnumWallType.WINDOW){
						wallModels.add(new TRSRBakedModel(getBakedModel(module.getWindowLocation(stateStoraged.getContainer()), modelState, format, bakedTextureGetter), 0F, 0.5F, 0F, 1F));
					}else{
						loc = walls[4];
					}
				}
				wallModels.add(getBakedModel(walls[1], modelState, format, bakedTextureGetter));
			}else if(size == EnumModuleSize.MIDDLE){
				if(wallType != EnumWallType.NONE){
					if(moduleSize == EnumModuleSize.SMALL){
						if(wallType == EnumWallType.WINDOW){
							wallModels.add(new TRSRBakedModel(getBakedModel(module.getWindowLocation(stateStoraged.getContainer()), modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
						}else{
							loc = walls[3];
						}
					}else if(moduleSize == EnumModuleSize.MIDDLE){	
						if(wallType == EnumWallType.WINDOW){
							wallModels.add(new TRSRBakedModel(getBakedModel(module.getWindowLocation(stateStoraged.getContainer()), modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
						}else{
							loc = walls[6];
						}
					}
				}
				wallModels.add(getBakedModel(walls[0], modelState, format, bakedTextureGetter));
			}else if(size == EnumModuleSize.LARGE){
				if(wallType != EnumWallType.NONE){
					if(moduleSize == EnumModuleSize.SMALL){
						if(wallType == EnumWallType.WINDOW){
							loc = module.getWindowLocation(stateStoraged.getContainer());
						}else{
							loc = walls[2];
						}
					}else if(moduleSize == EnumModuleSize.MIDDLE){	
						if(wallType == EnumWallType.WINDOW){
							wallModels.add(getBakedModel(module.getWindowLocation(stateStoraged.getContainer()), modelState, format, bakedTextureGetter));
						}else{
							loc = walls[5];
						}
					}else if(moduleSize == EnumModuleSize.LARGE){
						if(wallType == EnumWallType.WINDOW){
							wallModels.add(getBakedModel(module.getWindowLocation(stateStoraged.getContainer()), modelState, format, bakedTextureGetter));
						}else{
							loc = walls[7];
						}
					}
				}
			}
			if(loc != null){
				wallModels.add(getBakedModel(loc, modelState, format, bakedTextureGetter));
			}
		}
		if(size == EnumModuleSize.SMALL){
			wallModels.add(getBakedModel(walls[5], modelState, format, bakedTextureGetter));
		}else if(size == EnumModuleSize.MIDDLE){
			wallModels.add(getBakedModel(walls[2], modelState, format, bakedTextureGetter));
		}
		return wallModels;
	}

	@Override
	public void initModels(IModuleContainer container) {
		getModelOrDefault(drawer);
		getModelOrDefault(wall);
		for(ResourceLocation loc : walls){
			getModelOrDefault(loc);
		}
	}

}
