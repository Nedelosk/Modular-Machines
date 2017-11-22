package modularmachines.client.model.module;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModelProperty;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.common.modules.Module;
import modularmachines.common.modules.storages.modules.ModuleCasing;
import modularmachines.common.modules.storages.modules.ModuleModuleRack;

@SideOnly(Side.CLIENT)
public class ModelDataCasing extends ModelData {
	
	private enum Property implements IModelProperty {
		BASE, LEFT, RIGHT
	}
	
	public static void initModelData(ModelLocationBuilder location) {
		ModelDataCasing casing = new ModelDataCasing();
		casing.add(Property.BASE, location.copy().setPreFix("casing"));
		casing.add(Property.LEFT, location.copy().setPreFix("side_left"));
		casing.add(Property.RIGHT, location.copy().setPreFix("side_right"));
		location.data().setModel(casing);
	}
	
	@Override
	public IModuleModelState createState(Module module) {
		IModuleHandler moduleHandler = module.getParent();
		if (module instanceof ModuleCasing) {
			moduleHandler = ((ModuleCasing) module).getHandler();
		}
		ModuleModelState modelState = new ModuleModelState();
		Module left = moduleHandler.getModule(EnumCasingPositions.LEFT);
		Module right = moduleHandler.getModule(EnumCasingPositions.RIGHT);
		modelState.set(Property.LEFT, left == null || !(left instanceof ModuleModuleRack));
		modelState.set(Property.RIGHT, right == null || !(left instanceof ModuleModuleRack));
		return modelState;
	}
	
	@Override
	public void addModel(IModelList modelList, Module module, IModuleModelState modelState) {
		if (modelState.get(Property.LEFT)) {
			modelList.add(Property.LEFT);
		}
		if (modelState.get(Property.RIGHT)) {
			modelList.add(Property.RIGHT);
		}
		modelList.add(Property.BASE);
		/*for (Module otherModule : storage.getModules().getModules()) {
			if(otherModule != module){
				models.add(ModelLoader.getModel(otherModule, storage, modelState, format));
			}
		}*/
	}
}
