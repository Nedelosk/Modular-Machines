package modularmachines.client.model.module;

import net.minecraft.client.renderer.block.model.IBakedModel;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModelProperty;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.client.model.TRSRBakedModel;

@SideOnly(Side.CLIENT)
public class ModelDataModuleRack extends ModelData {
	
	private enum Property implements IModelProperty {
		STORAGE, TOP, BACK, WALL, STICK_DOWN, STICK_UP, SMALL_DOWN, SMALL_MEDIUM, SMALL_UP, MEDIUM_MEDIUM, MEDIUM_UP, LARGE
	}
	
	public static void initModelData(ModelLocationBuilder basicLocation) {
		ModelDataModuleRack storage = new ModelDataModuleRack();
		storage.add(Property.STORAGE, basicLocation.copy().setPreFix("storage"));
		storage.add(Property.TOP, basicLocation.copy().setPreFix("top"));
		storage.add(Property.BACK, basicLocation.setPreFix("back"));
		storage.add(Property.STICK_DOWN, basicLocation.copy().setPreFix("front_walls/stick_down"));
		storage.add(Property.STICK_UP, basicLocation.copy().setPreFix("front_walls/stick_up"));
		storage.add(Property.SMALL_DOWN, basicLocation.copy().setPreFix("front_walls/small_down"));
		storage.add(Property.SMALL_MEDIUM, basicLocation.copy().setPreFix("front_walls/small_medium"));
		storage.add(Property.SMALL_UP, basicLocation.copy().setPreFix("front_walls/small_up"));
		storage.add(Property.MEDIUM_MEDIUM, basicLocation.copy().setPreFix("front_walls/medium_medium"));
		storage.add(Property.MEDIUM_UP, basicLocation.copy().setPreFix("front_walls/medium_up"));
		storage.add(Property.LARGE, basicLocation.copy().setPreFix("front_walls/large"));
		basicLocation.data().setModel(storage);
	}
	
	@Override
	public void addModel(IModelList modelList, Module module, IModuleModelState modelState) {
		IModuleHandler moduleHandler = module.getParent();
		if (module instanceof IModuleProvider) {
			moduleHandler = ((IModuleProvider) module).getHandler();
		}
		//IStoragePosition position = moduleHandler.getPosition();
		IBakedModel bakedModel;
		/*if (position == EnumStoragePosition.TOP) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModelLoader.getModel(get(TOP), format));
			models.addAll(getStorageModels(moduleHandler, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		} else if (position == EnumStoragePosition.BACK) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModelLoader.getModel(get(BACK), format));
			models.addAll(getStorageModels(moduleHandler, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		}else{*/
		modelList.add(get(Property.STORAGE));
		EnumModuleSizes size = null;
		for (Module otherModule : moduleHandler.getModules()) {
			IModuleData data = otherModule.getData();
			size = EnumModuleSizes.getSize(size, data.getSize());
			if (size == EnumModuleSizes.MEDIUM) {
				modelList.add(Property.WALL);
			} else if (size == EnumModuleSizes.SMALL) {
				modelList.add(Property.WALL, m -> new TRSRBakedModel(m, 0F, 0.25F, 0F));
			}
		}
		addStorageModels(moduleHandler, modelList);
	}
	
	private void addStorageModels(IModuleHandler handler, IModelList modelList) {
		EnumModuleSizes size = null;
		for (Module module : handler.getModules()) {
			EnumModuleSizes moduleSize = module.getData().getSize();
			IBakedModel model = modelList.get(module);
			if (model != null) {
				if (size == null) {
					modelList.add(model);
				} else if (size == EnumModuleSizes.SMALL) {
					modelList.add(model, -0.25F);
				} else {
					modelList.add(model, -0.5F);
				}
			}
			size = EnumModuleSizes.getSize(size, moduleSize);
			addWallModels(module, size, moduleSize, module.getWallType(), modelList);
		}
		if (size == null) {
			//models.add(ModelLoader.getModel(get(LARGE), format));
		} else if (size == EnumModuleSizes.SMALL) {
			//models.add(ModelLoader.getModel(get(MEDIUM_MEDIUM), format));
		} else if (size == EnumModuleSizes.MEDIUM) {
			//models.add(ModelLoader.getModel(get(SMALL_DOWN), format));
		}
	}
	
	private void addWallModels(Module module, EnumModuleSizes size, EnumModuleSizes moduleSize, EnumWallType wallType, IModelList models) {
		IBakedModel windowModel = models.get(module.getWindowLocation());
		if (size == EnumModuleSizes.SMALL) {
			if (wallType != EnumWallType.NONE) {
				if (wallType == EnumWallType.WINDOW) {
					models.add(windowModel, 0.5F);
				} else {
					models.add(Property.SMALL_UP);
				}
			}
			models.add(Property.STICK_UP);
		} else if (size == EnumModuleSizes.MEDIUM) {
			if (wallType != EnumWallType.NONE) {
				if (moduleSize == EnumModuleSizes.SMALL) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel, 0.25F);
					} else {
						models.add(Property.SMALL_MEDIUM);
					}
				} else if (moduleSize == EnumModuleSizes.MEDIUM) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel, 0.25F);
					} else {
						models.add(Property.MEDIUM_UP);
					}
				}
			}
			models.add(Property.STICK_DOWN);
		} else if (size == EnumModuleSizes.LARGE) {
			if (wallType != EnumWallType.NONE) {
				if (moduleSize == EnumModuleSizes.SMALL) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(module.getWindowLocation());
					} else {
						models.add(Property.SMALL_DOWN);
					}
				} else if (moduleSize == EnumModuleSizes.MEDIUM) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel);
					} else {
						models.add(Property.MEDIUM_MEDIUM);
					}
				} else if (moduleSize == EnumModuleSizes.LARGE) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel);
					} else {
						models.add(Property.LARGE);
					}
				}
			}
		}
	}
}
