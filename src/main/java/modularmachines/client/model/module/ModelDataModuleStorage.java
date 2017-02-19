package modularmachines.client.model.module;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.model.ModelLocation;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.model.TRSRBakedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDataModuleStorage extends ModelData {

	public static final String STORAGE = "storage";
	public static final String TOP = "top";
	public static final String BACK = "back";
	public static final String WALL = "wall";
	public static final String STICK_DOWN = "stick_down";
	public static final String STICK_UP = "stick_up";
	public static final String SMALL_DOWN = "small_down";
	public static final String SMALL_MEDIUM = "small_medium";
	public static final String SMALL_UP = "small_up";
	public static final String MEDIUM_MEDIUM = "medium_medium";
	public static final String MEDIUM_UP = "medium_up";
	public static final String LARGE = "large";
	
	public static void initModelData(ModelLocation basicLocation){
		ModelDataModuleStorage storage = new ModelDataModuleStorage();
		storage.addLocation(STORAGE, new ModelLocation(basicLocation).addPreFix("storage"));
		storage.addLocation(TOP, new ModelLocation(basicLocation).addPreFix("top"));
		storage.addLocation(BACK, new ModelLocation(basicLocation).addPreFix("back"));
		storage.addLocation(STICK_DOWN, new ModelLocation(basicLocation).addPreFix("front_walls/stick_down"));
		storage.addLocation(STICK_UP, new ModelLocation(basicLocation).addPreFix("front_walls/stick_up"));
		storage.addLocation(SMALL_DOWN, new ModelLocation(basicLocation).addPreFix("front_walls/small_down"));
		storage.addLocation(SMALL_MEDIUM, new ModelLocation(basicLocation).addPreFix("front_walls/small_medium"));
		storage.addLocation(SMALL_UP, new ModelLocation(basicLocation).addPreFix("front_walls/small_up"));
		storage.addLocation(MEDIUM_MEDIUM, new ModelLocation(basicLocation).addPreFix("front_walls/medium_medium"));
		storage.addLocation(MEDIUM_UP, new ModelLocation(basicLocation).addPreFix("front_walls/medium_up"));
		storage.addLocation(LARGE, new ModelLocation(basicLocation).addPreFix("front_walls/large"));
		basicLocation.getData().addModel(TileEntity.class, storage);
	}

	private List<IBakedModel> getStorageModels(IStorage storage, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		List<IBakedModel> models = new ArrayList<>();
		EnumModuleSizes size = null;
		for (Module module : storage.getStorage().getModules()) {
			if(module != storage.getModule()){
				EnumModuleSizes moduleSize = module.getData().getSize();
				IBakedModel model = ModelLoader.getModel(module, storage, modelState, format);
				if (model != null) {
					if (size == null) {
						models.add(model);
					} else if (size == EnumModuleSizes.SMALL) {
						models.add(createModel(model, -0.25F));
					} else {
						models.add(createModel(model, -0.5F));
					}
				}
				size = EnumModuleSizes.getSize(size, moduleSize);
				addWallModels(module, size, moduleSize, module.getWallType(), format, models);
			}
		}
		if (size == null) {
			models.add(ModelLoader.getModel(get(LARGE), format));
		} else if (size == EnumModuleSizes.SMALL) {
			models.add(ModelLoader.getModel(get(MEDIUM_MEDIUM), format));
		} else if (size == EnumModuleSizes.MEDIUM) {
			models.add(ModelLoader.getModel(get(SMALL_DOWN), format));
		}
		return models;
		
	}
	
	private void addWallModels(Module module, EnumModuleSizes size, EnumModuleSizes moduleSize, EnumWallType wallType, VertexFormat format, List<IBakedModel> models){
		ResourceLocation location = null;
		IBakedModel windowModel = getWindowModel(module, format);
		if (size == EnumModuleSizes.SMALL) {
			if (wallType != EnumWallType.NONE) {
				if (wallType == EnumWallType.WINDOW) {
					models.add(createModel(windowModel, 0.5F));
				} else {
					location = get(SMALL_UP);
				}
			}
			models.add(ModelLoader.getModel(get(STICK_UP), format));
		} else if (size == EnumModuleSizes.MEDIUM) {
			if (wallType != EnumWallType.NONE) {
				if (moduleSize == EnumModuleSizes.SMALL) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(createModel(windowModel, 0.25F));
					} else {
						location = get(SMALL_MEDIUM);
					}
				} else if (moduleSize == EnumModuleSizes.MEDIUM) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(createModel(windowModel, 0.25F));
					} else {
						location = get(MEDIUM_UP);
					}
				}
			}
			models.add(ModelLoader.getModel(get(STICK_DOWN), format));
		} else if (size == EnumModuleSizes.LARGE) {
			if (wallType != EnumWallType.NONE) {
				if (moduleSize == EnumModuleSizes.SMALL) {
					if (wallType == EnumWallType.WINDOW) {
						location = module.getWindowLocation();
					} else {
						location = get(SMALL_DOWN);
					}
				} else if (moduleSize == EnumModuleSizes.MEDIUM) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel);
					} else {
						location = get(MEDIUM_MEDIUM);
					}
				} else if (moduleSize == EnumModuleSizes.LARGE) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(windowModel);
					} else {
						location = get(LARGE);
					}
				}
			}
		}
		if (location != null) {
			models.add(ModelLoader.getModel(location, format));
		}
	}
	
	private IBakedModel getWindowModel(Module module, VertexFormat format){
		return ModelLoader.getModel(module.getWindowLocation(), format);
	}

	@Override
	public IBakedModel getModel(Module module, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		IStoragePosition position = storage.getPosition();
		IModuleStorage moduleStorage = storage.getStorage();
		IBakedModel bakedModel;
		if (position == EnumStoragePosition.TOP) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModelLoader.getModel(get(TOP), format));
			models.addAll(getStorageModels(storage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		} else if (position == EnumStoragePosition.BACK) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModelLoader.getModel(get(BACK), format));
			models.addAll(getStorageModels(storage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		}else{
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModelLoader.getModel(get(STORAGE), format));
			EnumModuleSizes size = null;
			for (Module storagedModule : moduleStorage.getModules()) {
				ModuleData data = storagedModule.getData();
				if (!(data.isStorage(position))) {
					size = EnumModuleSizes.getSize(size, storagedModule.getData().getSize());
					if (size == EnumModuleSizes.MEDIUM) {
						models.add(ModelLoader.getModel(get(WALL), format));
					} else if (size == EnumModuleSizes.SMALL) {
						models.add(new TRSRBakedModel(ModelLoader.getModel(get(WALL), format), 0F, 0.25F, 0F));
					}
				}
			}
			models.addAll(getStorageModels(storage, modelState, format, bakedTextureGetter));
			if(!models.isEmpty()){
				bakedModel = new BakedMultiModel(models);
			}else{
				bakedModel = ModelLoaderRegistry.getMissingModel().bake(modelState, format, bakedTextureGetter);
			}
		}
		return bakedModel;
	}
}
