package modularmachines.client.model.module;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.model.DefaultProperty;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.common.core.Constants;
import modularmachines.common.modules.IModuleWorking;

@SideOnly(Side.CLIENT)
public class ModelDataWorking extends ModelData {
	
	public static void addModelData(ModelLocationBuilder locationOn, ModelLocationBuilder locationOff) {
		ModelDataWorking working = new ModelDataWorking();
		working.add(DefaultProperty.ON, locationOn);
		working.add(DefaultProperty.OFF, locationOff);
		locationOn.data().setModel(working);
	}
	
	public static void addModelData(IModuleData moduleData) {
		ModelDataWorking working = new ModelDataWorking();
		working.add(DefaultProperty.ON, new ResourceLocation(Constants.MOD_ID, "module/" + moduleData.getRegistryName().getResourcePath() + "_on"));
		working.add(DefaultProperty.OFF, new ResourceLocation(Constants.MOD_ID, "module/" + moduleData.getRegistryName().getResourcePath() + "_off"));
		moduleData.setModel(working);
	}
	
	@Override
	public IModuleModelState createState(Module module) {
		boolean isWorking = false;
		if (module instanceof IModuleWorking) {
			IModuleWorking working = (IModuleWorking) module;
			isWorking = working.isWorking();
		}
		ModuleModelState modelState = new ModuleModelState();
		modelState.set(DefaultProperty.ON, isWorking);
		return modelState;
	}
	
	@Override
	public void addModel(IModelList modelList, Module module, IModuleModelState modelState) {
		if (modelState.get(DefaultProperty.ON)) {
			modelList.add(DefaultProperty.ON);
		} else {
			modelList.add(DefaultProperty.OFF);
		}
	}
}
