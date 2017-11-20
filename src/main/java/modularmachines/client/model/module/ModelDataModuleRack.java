package modularmachines.client.model.module;

import net.minecraft.client.renderer.block.model.IBakedModel;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModelProperty;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.api.modules.positions.EnumRackPositions;
import modularmachines.api.modules.positions.IModulePosition;
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
	public void addModel(IModelList modelList, Module storageModule, IModuleModelState modelState) {
		IModuleHandler moduleHandler = storageModule.getParent();
		if (storageModule instanceof IModuleProvider) {
			moduleHandler = ((IModuleProvider) storageModule).getHandler();
		}
		modelList.add(Property.STORAGE);
		for (int i = 0; i < EnumRackPositions.values().length; i++) {
			IModulePosition position = EnumRackPositions.values()[i];
			Module module = moduleHandler.getModule(position);
			if (module != null) {
				addModule(module, i, modelList);
			}
		}
	}
	
	public void addModule(Module module, int positionIndex, IModelList modelList) {
		final float offset = -0.25F * positionIndex;
		IBakedModel model = modelList.getModel(module);
		if (model != null) {
			modelList.add(module.getData().getWallModelLocation(), m -> new TRSRBakedModel(m, 0.0F, offset, 0.0F));
		}
	}
}
