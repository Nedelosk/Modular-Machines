package modularmachines.client.model.module;

import net.minecraft.client.renderer.block.model.IBakedModel;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.model.IModelKey;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.client.model.TRSRBakedModel;

@SideOnly(Side.CLIENT)
public class ModelDataModuleRack extends ModelData {
	
	private enum ModelKey implements IModelKey {
		STORAGE, TOP, BACK, WALL, STICK_DOWN, STICK_UP, SMALL_DOWN, SMALL_MEDIUM, SMALL_UP, MEDIUM_MEDIUM, MEDIUM_UP, LARGE
	}
	
	public static void initModelData(ModelLocationBuilder basicLocation) {
		ModelDataModuleRack storage = new ModelDataModuleRack();
		storage.add(ModelKey.STORAGE, basicLocation.copy().addPreFix("storage"));
		storage.add(ModelKey.TOP, basicLocation.copy().addPreFix("top"));
		storage.add(ModelKey.BACK, basicLocation.addPreFix("back"));
		storage.add(ModelKey.STICK_DOWN, basicLocation.copy().addPreFix("front_walls/stick_down"));
		storage.add(ModelKey.STICK_UP, basicLocation.copy().addPreFix("front_walls/stick_up"));
		storage.add(ModelKey.SMALL_DOWN, basicLocation.copy().addPreFix("front_walls/small_down"));
		storage.add(ModelKey.SMALL_MEDIUM, basicLocation.copy().addPreFix("front_walls/small_medium"));
		storage.add(ModelKey.SMALL_UP, basicLocation.copy().addPreFix("front_walls/small_up"));
		storage.add(ModelKey.MEDIUM_MEDIUM, basicLocation.copy().addPreFix("front_walls/medium_medium"));
		storage.add(ModelKey.MEDIUM_UP, basicLocation.copy().addPreFix("front_walls/medium_up"));
		storage.add(ModelKey.LARGE, basicLocation.copy().addPreFix("front_walls/large"));
		basicLocation.data().setModel(storage);
	}
	
	@Override
	public void addModel(IModelList modelList, Module module) {
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
		modelList.add(get(ModelKey.STORAGE));
		EnumModuleSizes size = null;
		for (Module otherModule : moduleHandler.getModules()) {
			ModuleData data = otherModule.getData();
			size = EnumModuleSizes.getSize(size, data.getSize());
			if (size == EnumModuleSizes.MEDIUM) {
				modelList.add(ModelKey.WALL);
			} else if (size == EnumModuleSizes.SMALL) {
				modelList.add(ModelKey.WALL, m -> new TRSRBakedModel(m, 0F, 0.25F, 0F));
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
					models.add(ModelKey.SMALL_UP);
				}
			}
			models.add(get(ModelKey.STICK_UP));
		} else if (size == EnumModuleSizes.MEDIUM) {
			if (wallType != EnumWallType.NONE) {
				if (moduleSize == EnumModuleSizes.SMALL) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel, 0.25F);
					} else {
						models.add(ModelKey.SMALL_MEDIUM);
					}
				} else if (moduleSize == EnumModuleSizes.MEDIUM) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel, 0.25F);
					} else {
						models.add(ModelKey.MEDIUM_UP);
					}
				}
			}
			models.add(get(ModelKey.STICK_DOWN));
		} else if (size == EnumModuleSizes.LARGE) {
			if (wallType != EnumWallType.NONE) {
				if (moduleSize == EnumModuleSizes.SMALL) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(module.getWindowLocation());
					} else {
						models.add(ModelKey.SMALL_DOWN);
					}
				} else if (moduleSize == EnumModuleSizes.MEDIUM) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel);
					} else {
						models.add(ModelKey.MEDIUM_MEDIUM);
					}
				} else if (moduleSize == EnumModuleSizes.LARGE) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel);
					} else {
						models.add(ModelKey.LARGE);
					}
				}
			}
		}
	}
}
