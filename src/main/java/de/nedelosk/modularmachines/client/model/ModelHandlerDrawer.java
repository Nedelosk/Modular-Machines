package de.nedelosk.modularmachines.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.EnumWallType;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.BakedMultiModel;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelHelper;
import de.nedelosk.modularmachines.api.modules.position.EnumStoragePositions;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IDefaultModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleModuleStorage;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHandlerDrawer extends ModelHandler<IModuleModuleStorage> implements IModelHandler<IModuleModuleStorage>, IModelInitHandler{

	/**
	 * 0 = stick_down
	 * 1 = stick_up
	 * 2 = small_down
	 * 3 = small_medium
	 * 4 = small_up
	 * 5 = medium_medium
	 * 6 = medium_up
	 * 7 = large
	 */
	private final ResourceLocation[] walls;
	private final ResourceLocation storageModel;
	private final ResourceLocation top;
	private final ResourceLocation back;
	private final ResourceLocation wall;

	public ModelHandlerDrawer(IModuleContainer container) {
		super("module_storage", container);
		storageModel = getModelLocation("drawer");
		top = getModelLocation("top");
		back = getModelLocation("back");
		wall = getModelLocation("wall");
		walls = new ResourceLocation[]{ 
				getModelLocation("front_walls/stick_down"),
				getModelLocation("front_walls/stick_up"),
				getModelLocation("front_walls/small_down"),
				getModelLocation("front_walls/small_medium"),
				getModelLocation("front_walls/small_up"),
				getModelLocation("front_walls/medium_medium"),
				getModelLocation("front_walls/medium_up"),
				getModelLocation("front_walls/large")
		};
	}

	@Override
	public void reload(IModuleState<IModuleModuleStorage> state, IStorage storage, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		IStoragePosition position = storage.getPosition();
		IDefaultModuleStorage moduleStorage = (IDefaultModuleStorage) storage;
		if(position != EnumStoragePositions.BACK && position != EnumStoragePositions.TOP){
			List<IBakedModel> models = new ArrayList<>();
			models.add(getBakedModel(storageModel, modelState, format, bakedTextureGetter));
			List<IModuleState> modules = new ArrayList<>();

			for(IModuleState stateStoraged : moduleStorage.getModules()){
				if(stateStoraged != null){
					modules.add(stateStoraged);
				}
			}
			EnumModuleSizes size = null;
			for(IModuleState storagedState : modules){
				if(!(storagedState.getModule() instanceof IModuleModuleStorage)){
					size = EnumModuleSizes.getSize(size, storagedState.getModule().getSize(storagedState.getContainer()));
					if(size == EnumModuleSizes.MEDIUM){
						models.add(getBakedModel(wall, modelState, format, bakedTextureGetter));
					}else if(size == EnumModuleSizes.SMALL){
						models.add(new TRSRBakedModel(getBakedModel(wall, modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
					}
				}
			}
			models.addAll(getStorageModels(moduleStorage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		}else if(position == EnumStoragePositions.TOP){
			bakedModel = getBakedModel(top, modelState, format, bakedTextureGetter);
		}else if(position == EnumStoragePositions.BACK){
			bakedModel = getBakedModel(back, modelState, format, bakedTextureGetter);
		}
		//Rotate Modules
		bakedModel = new TRSRBakedModel(bakedModel, 0F, 0F, 0F, 0F, position.getRotation(), 0F, 1F);
	}

	public List<IBakedModel> getStorageModels(IDefaultModuleStorage storage, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter){
		List<IBakedModel> models = new ArrayList<>();
		EnumModuleSizes size = null;
		List<IModuleState> modules = new ArrayList<>();
		for(IModuleState stateStoraged : storage.getModules()){
			if(stateStoraged != null){
				modules.add(stateStoraged);
			}
		}
		for(IModuleState moduleState : modules){
			EnumModuleSizes moduleSize = moduleState.getModule().getSize(moduleState.getContainer());
			IModule module = moduleState.getModule();
			if(((IModuleStateClient)moduleState).getModelHandler() != null){
				IBakedModel model = ModuleModelHelper.getModel(moduleState, storage, modelState, format);
				if(model != null){
					if(size == null){
						models.add(model);
					}else if(size == EnumModuleSizes.SMALL){
						models.add(new TRSRBakedModel(model, 0F, -0.25F, 0F, 1F));
					}else{
						models.add(new TRSRBakedModel(model, 0F, -0.5F, 0F, 1F));
					}
				}
			}
			size = EnumModuleSizes.getSize(size, moduleSize);
			EnumWallType wallType = moduleState.getModule().getWallType(moduleState);
			ResourceLocation location = null;
			if(size == EnumModuleSizes.SMALL){
				if(wallType != EnumWallType.NONE){
					if(wallType == EnumWallType.WINDOW){
						models.add(new TRSRBakedModel(getBakedModel(module.getWindowLocation(moduleState.getContainer()), modelState, format, bakedTextureGetter), 0F, 0.5F, 0F, 1F));
					}else{
						location = walls[4];
					}
				}
				models.add(getBakedModel(walls[1], modelState, format, bakedTextureGetter));
			}else if(size == EnumModuleSizes.MEDIUM){
				if(wallType != EnumWallType.NONE){
					if(moduleSize == EnumModuleSizes.SMALL){
						if(wallType == EnumWallType.WINDOW){
							models.add(new TRSRBakedModel(getBakedModel(module.getWindowLocation(moduleState.getContainer()), modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
						}else{
							location = walls[3];
						}
					}else if(moduleSize == EnumModuleSizes.MEDIUM){	
						if(wallType == EnumWallType.WINDOW){
							models.add(new TRSRBakedModel(getBakedModel(module.getWindowLocation(moduleState.getContainer()), modelState, format, bakedTextureGetter), 0F, 0.25F, 0F, 1F));
						}else{
							location = walls[6];
						}
					}
				}
				models.add(getBakedModel(walls[0], modelState, format, bakedTextureGetter));
			}else if(size == EnumModuleSizes.LARGE){
				if(wallType != EnumWallType.NONE){
					if(moduleSize == EnumModuleSizes.SMALL){
						if(wallType == EnumWallType.WINDOW){
							location = module.getWindowLocation(moduleState.getContainer());
						}else{
							location = walls[2];
						}
					}else if(moduleSize == EnumModuleSizes.MEDIUM){	
						if(wallType == EnumWallType.WINDOW){
							models.add(getBakedModel(module.getWindowLocation(moduleState.getContainer()), modelState, format, bakedTextureGetter));
						}else{
							location = walls[5];
						}
					}else if(moduleSize == EnumModuleSizes.LARGE){
						if(wallType == EnumWallType.WINDOW){
							models.add(getBakedModel(module.getWindowLocation(moduleState.getContainer()), modelState, format, bakedTextureGetter));
						}else{
							location = walls[7];
						}
					}
				}
			}
			if(location != null){
				models.add(getBakedModel(location, modelState, format, bakedTextureGetter));
			}
		}
		if(size == null){
			models.add(getBakedModel(walls[7], modelState, format, bakedTextureGetter));
		}else if(size == EnumModuleSizes.SMALL){
			models.add(getBakedModel(walls[5], modelState, format, bakedTextureGetter));
		}else if(size == EnumModuleSizes.MEDIUM){
			models.add(getBakedModel(walls[2], modelState, format, bakedTextureGetter));
		}
		return models;
	}

	@Override
	public void initModels(IModuleContainer container) {
		getModelOrDefault(storageModel);
		getModelOrDefault(wall);
		for(ResourceLocation loc : walls){
			getModelOrDefault(loc);
		}
	}

}
