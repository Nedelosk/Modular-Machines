package modularmachines.client.model.module;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.EnumModulePositions;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.IModelKey;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.common.modules.storages.modules.ModuleCasing;

@SideOnly(Side.CLIENT)
public class ModelDataCasing extends ModelData {
	
	private enum Key implements IModelKey {
		BASE, LEFT, RIGHT
	}
	
	public static final String CASING = "casing";
	public static final String CASING_LEFT = "casingLeft";
	public static final String CASING_RIGHT = "casingRight";
	
	public static void initModelData(ModelLocationBuilder location) {
		ModelDataCasing casing = new ModelDataCasing();
		casing.add(Key.BASE, location.copy().addPreFix("casing"));
		casing.add(Key.LEFT, location.copy().addPreFix("side_left"));
		casing.add(Key.RIGHT, location.copy().addPreFix("side_right"));
		location.data().setModel(casing);
	}
	
	@Override
	public void addModel(IModelList modelList, Module module) {
		IModuleHandler moduleHandler = module.getParent();
		if (module instanceof ModuleCasing) {
			moduleHandler = ((ModuleCasing) module).getHandler();
		}
		if (!moduleHandler.hasModule(EnumModulePositions.LEFT)) {
			modelList.add(Key.LEFT);
		}
		if (!moduleHandler.hasModule(EnumModulePositions.RIGHT)) {
			modelList.add(Key.RIGHT);
		}
		modelList.add(Key.BASE);
		/*for (Module otherModule : storage.getModules().getModules()) {
			if(otherModule != module){
				models.add(ModelLoader.getModel(otherModule, storage, modelState, format));
			}
		}*/
	}
}
