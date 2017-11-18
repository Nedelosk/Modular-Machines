package modularmachines.client.model.module;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.model.DefaultKey;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.common.modules.IModuleWorking;

@SideOnly(Side.CLIENT)
public class ModelDataWorking extends ModelData {
	
	public static void initModelData(ModuleData data, ModelLocationBuilder locationOn, ModelLocationBuilder locationOff) {
		ModelDataWorking working = new ModelDataWorking();
		working.add(DefaultKey.ON, locationOn);
		working.add(DefaultKey.OFF, locationOff);
		data.setModel(working);
	}
	
	@Override
	public void addModel(IModelList modelList, Module module) {
		if (module instanceof IModuleWorking) {
			IModuleWorking working = (IModuleWorking) module;
			if (working.isWorking()) {
				modelList.add(DefaultKey.ON);
			} else {
				modelList.add(DefaultKey.OFF);
			}
		}
	}
}
