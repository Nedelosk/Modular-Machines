package modularmachines.client.model.module;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IMachineStoragePosition;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.model.TRSRBakedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDataModuleStorage extends ModelData {

	private final ResourceLocation storageModel;
	private final ResourceLocation top;
	private final ResourceLocation back;
	private final ResourceLocation wall;
	/**
	 * 0 = stick_down 1 = stick_up 2 = small_down 3 = small_medium 4 = small_up
	 * 5 = medium_medium 6 = medium_up 7 = large
	 */
	private final ResourceLocation[] walls;

	public ModelDataModuleStorage(ResourceLocation storageModel, ResourceLocation top, ResourceLocation back, ResourceLocation wall, ResourceLocation[] walls) {
		this.storageModel = storageModel;
		this.top = top;
		this.back = back;
		this.wall = wall;
		this.walls = walls;
	}

	public List<IBakedModel> getStorageModels(IStorage storage, IModelState modelState, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		List<IBakedModel> models = new ArrayList<>();
		EnumModuleSizes size = null;
		for (Module module : storage.getStorage().getModules()) {
			EnumModuleSizes moduleSize = module.getData().getSize();
			IBakedModel model = ModelLoader.getModel(module, storage, modelState, format);
			if (model != null) {
				if (size == null) {
					models.add(model);
				} else if (size == EnumModuleSizes.SMALL) {
					models.add(new TRSRBakedModel(model, 0F, -0.25F, 0F));
				} else {
					models.add(new TRSRBakedModel(model, 0F, -0.5F, 0F));
				}
			}
			size = EnumModuleSizes.getSize(size, moduleSize);
			EnumWallType wallType = module.getWallType();
			ResourceLocation location = null;
			if (size == EnumModuleSizes.SMALL) {
				if (wallType != EnumWallType.NONE) {
					if (wallType == EnumWallType.WINDOW) {
						models.add(new TRSRBakedModel(ModelLoader.getModel(module.getWindowLocation(), format), 0F, 0.5F, 0F));
					} else {
						location = walls[4];
					}
				}
				models.add(ModelLoader.getModel(walls[1], format));
			} else if (size == EnumModuleSizes.MEDIUM) {
				if (wallType != EnumWallType.NONE) {
					if (moduleSize == EnumModuleSizes.SMALL) {
						if (wallType == EnumWallType.WINDOW) {
							models.add(new TRSRBakedModel(ModelLoader.getModel(module.getWindowLocation(), format), 0F, 0.25F, 0F));
						} else {
							location = walls[3];
						}
					} else if (moduleSize == EnumModuleSizes.MEDIUM) {
						if (wallType == EnumWallType.WINDOW) {
							models.add(new TRSRBakedModel(ModelLoader.getModel(module.getWindowLocation(), format), 0F, 0.25F, 0F));
						} else {
							location = walls[6];
						}
					}
				}
				models.add(ModelLoader.getModel(walls[0], format));
			} else if (size == EnumModuleSizes.LARGE) {
				if (wallType != EnumWallType.NONE) {
					if (moduleSize == EnumModuleSizes.SMALL) {
						if (wallType == EnumWallType.WINDOW) {
							location = module.getWindowLocation();
						} else {
							location = walls[2];
						}
					} else if (moduleSize == EnumModuleSizes.MEDIUM) {
						if (wallType == EnumWallType.WINDOW) {
							models.add(ModelLoader.getModel(module.getWindowLocation(), format));
						} else {
							location = walls[5];
						}
					} else if (moduleSize == EnumModuleSizes.LARGE) {
						if (wallType == EnumWallType.WINDOW) {
							models.add(ModelLoader.getModel(module.getWindowLocation(), format));
						} else {
							location = walls[7];
						}
					}
				}
			}
			if (location != null) {
				models.add(ModelLoader.getModel(location, format));
			}
		}
		if (size == null) {
			models.add(ModelLoader.getModel(walls[7], format));
		} else if (size == EnumModuleSizes.SMALL) {
			models.add(ModelLoader.getModel(walls[5], format));
		} else if (size == EnumModuleSizes.MEDIUM) {
			models.add(ModelLoader.getModel(walls[2], format));
		}
		return models;
	}

	@Override
	public IBakedModel getModel(Module module, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		IStoragePosition position = storage.getPosition();
		IModuleStorage moduleStorage = storage.getStorage();
		IBakedModel bakedModel;
		if (position == EnumStoragePosition.TOP) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModelLoader.getModel(top, format));
			models.addAll(getStorageModels(storage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		} else if (position == EnumStoragePosition.BACK) {
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModelLoader.getModel(back, format));
			models.addAll(getStorageModels(storage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		}else{
			List<IBakedModel> models = new ArrayList<>();
			models.add(ModelLoader.getModel(storageModel, format));
			EnumModuleSizes size = null;
			for (Module storagedModule : moduleStorage.getModules()) {
				if (!(storagedModule.getData().isStorage(position))) {
					size = EnumModuleSizes.getSize(size, storagedModule.getData().getSize());
					if (size == EnumModuleSizes.MEDIUM) {
						models.add(ModelLoader.getModel(wall, format));
					} else if (size == EnumModuleSizes.SMALL) {
						models.add(new TRSRBakedModel(ModelLoader.getModel(wall, format), 0F, 0.25F, 0F));
					}
				}
			}
			models.addAll(getStorageModels(storage, modelState, format, bakedTextureGetter));
			bakedModel = new BakedMultiModel(models);
		}
		// Rotate Modules
		float rotation = 0F;
		if(position instanceof IMachineStoragePosition){
			rotation = ((IMachineStoragePosition)position).getRotation();
		}
		return new TRSRBakedModel(bakedModel, 0F, 0F, 0F, 0F, rotation, 0F);
	}
}
