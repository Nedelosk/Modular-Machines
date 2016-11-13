package modularmachines.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modular.ExpandedStoragePositions;
import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.models.BakedMultiModel;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandler;
import modularmachines.api.modules.models.ModuleModelLoader;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.state.IModuleStateClient;
import modularmachines.api.modules.storage.IStorage;
import modularmachines.api.modules.storage.module.IDefaultModuleStorage;
import modularmachines.api.modules.storage.module.IModuleModuleStorage;

@SideOnly(Side.CLIENT)
public class ModelHandlerModuleStorage extends ModelHandler<IModuleModuleStorage> implements IModelHandler<IModuleModuleStorage> {

	private final ResourceLocation storageModel;
	private final ResourceLocation top;
	private final ResourceLocation back;
	private final ResourceLocation wall;
	/**
	 * 0 = stick_down 1 = stick_up 2 = small_down 3 = small_medium 4 = small_up
	 * 5 = medium_medium 6 = medium_up 7 = large
	 */
	private final ResourceLocation[] walls;

	public ModelHandlerModuleStorage(ResourceLocation storageModel, ResourceLocation top, ResourceLocation back, ResourceLocation wall, ResourceLocation[] walls) {
		this.storageModel = storageModel;
		this.top = top;
		this.back = back;
		this.wall = wall;
		this.walls = walls;
	}

	@Override
	public void reload(IModuleState<IModuleModuleStorage> state, IStorage storage, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		IStoragePosition position = storage.getPosition();
		IDefaultModuleStorage moduleStorage = (IDefaultModuleStorage) storage;
		if (position != ExpandedStoragePositions.BACK && position != ExpandedStoragePositions.TOP) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModuleModelLoader.getModel(storageModel, format));
			List<IModuleState> modules = new ArrayList<>();
			for (IModuleState stateStoraged : moduleStorage.getModules()) {
				if (stateStoraged != null) {
					modules.add(stateStoraged);
				}
			}
			EnumModuleSizes size = null;
			for (IModuleState storagedState : modules) {
				if (!(storagedState.getModule() instanceof IModuleModuleStorage)) {
					size = EnumModuleSizes.getSize(size, storagedState.getContainer().getItemContainer().getSize());
					if (size == EnumModuleSizes.MEDIUM) {
						models.add(ModuleModelLoader.getModel(wall, format));
					} else if (size == EnumModuleSizes.SMALL) {
						models.add(new TRSRBakedModel(ModuleModelLoader.getModel(wall, format), 0F, 0.25F, 0F, 1F));
					}
				}
			}
			models.addAll(getStorageModels(moduleStorage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		} else if (position == ExpandedStoragePositions.TOP) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModuleModelLoader.getModel(top, format));
			List<IModuleState> modules = new ArrayList<>();
			for (IModuleState stateStoraged : moduleStorage.getModules()) {
				if (stateStoraged != null) {
					modules.add(stateStoraged);
				}
			}
			models.addAll(getStorageModels(moduleStorage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		} else if (position == ExpandedStoragePositions.BACK) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModuleModelLoader.getModel(back, format));
			List<IModuleState> modules = new ArrayList<>();
			for (IModuleState stateStoraged : moduleStorage.getModules()) {
				if (stateStoraged != null) {
					modules.add(stateStoraged);
				}
			}
			models.addAll(getStorageModels(moduleStorage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		}
		// Rotate Modules
		bakedModel = new TRSRBakedModel(bakedModel, 0F, 0F, 0F, 0F, position.getRotation(), 0F, 1F);
	}

	public List<IBakedModel> getStorageModels(IDefaultModuleStorage storage, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		List<IBakedModel> models = new ArrayList<>();
		EnumModuleSizes size = null;
		List<IModuleState> modules = new ArrayList<>();
		for (IModuleState stateStoraged : storage.getModules()) {
			if (stateStoraged != null) {
				modules.add(stateStoraged);
			}
		}
		for (IModuleState moduleState : modules) {
			EnumModuleSizes moduleSize = moduleState.getContainer().getItemContainer().getSize();
			IModule module = moduleState.getModule();
			IModuleItemContainer itemContainer = moduleState.getContainer().getItemContainer();
			if (moduleState instanceof IModuleStateClient && ((IModuleStateClient) moduleState).getModelHandler() != null) {
				IBakedModel model = ModuleModelLoader.getModel(moduleState, storage, modelState, format);
				if (model != null) {
					if (size == null) {
						models.add(model);
					} else if (size == EnumModuleSizes.SMALL) {
						models.add(new TRSRBakedModel(model, 0F, -0.25F, 0F, 1F));
					} else {
						models.add(new TRSRBakedModel(model, 0F, -0.5F, 0F, 1F));
					}
				}
			}
			size = EnumModuleSizes.getSize(size, moduleSize);
			EnumWallType wallType = moduleState.getModule().getWallType(moduleState);
			ResourceLocation location = null;
			if (size == EnumModuleSizes.SMALL) {
				if (wallType != EnumWallType.NONE) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(new TRSRBakedModel(ModuleModelLoader.getModel(module.getWindowLocation(moduleState.getContainer().getItemContainer()), format), 0F, 0.5F, 0F, 1F));
					} else {
						location = walls[4];
					}
				}
				models.add(ModuleModelLoader.getModel(walls[1], format));
			} else if (size == EnumModuleSizes.MEDIUM) {
				if (wallType != EnumWallType.NONE) {
					if (moduleSize == EnumModuleSizes.SMALL) {
						if (wallType == EnumWallType.WINDOW) {
							models.add(new TRSRBakedModel(ModuleModelLoader.getModel(module.getWindowLocation(itemContainer), format), 0F, 0.25F, 0F, 1F));
						} else {
							location = walls[3];
						}
					} else if (moduleSize == EnumModuleSizes.MEDIUM) {
						if (wallType == EnumWallType.WINDOW) {
							models.add(new TRSRBakedModel(ModuleModelLoader.getModel(module.getWindowLocation(itemContainer), format), 0F, 0.25F, 0F, 1F));
						} else {
							location = walls[6];
						}
					}
				}
				models.add(ModuleModelLoader.getModel(walls[0], format));
			} else if (size == EnumModuleSizes.LARGE) {
				if (wallType != EnumWallType.NONE) {
					if (moduleSize == EnumModuleSizes.SMALL) {
						if (wallType == EnumWallType.WINDOW) {
							location = module.getWindowLocation(itemContainer);
						} else {
							location = walls[2];
						}
					} else if (moduleSize == EnumModuleSizes.MEDIUM) {
						if (wallType == EnumWallType.WINDOW) {
							models.add(ModuleModelLoader.getModel(module.getWindowLocation(itemContainer), format));
						} else {
							location = walls[5];
						}
					} else if (moduleSize == EnumModuleSizes.LARGE) {
						if (wallType == EnumWallType.WINDOW) {
							models.add(ModuleModelLoader.getModel(module.getWindowLocation(itemContainer), format));
						} else {
							location = walls[7];
						}
					}
				}
			}
			if (location != null) {
				models.add(ModuleModelLoader.getModel(location, format));
			}
		}
		if (size == null) {
			models.add(ModuleModelLoader.getModel(walls[7], format));
		} else if (size == EnumModuleSizes.SMALL) {
			models.add(ModuleModelLoader.getModel(walls[5], format));
		} else if (size == EnumModuleSizes.MEDIUM) {
			models.add(ModuleModelLoader.getModel(walls[2], format));
		}
		return models;
	}
}
