package modularmachines.client.model.module;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.model.DefaultKey;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.ModelLocationBuilder;

@SideOnly(Side.CLIENT)
public class ModelDataDefault extends ModelData {
	public static void initModelData(ModelLocationBuilder location) {
		ModelDataDefault model = new ModelDataDefault();
		model.add(DefaultKey.INSTANCE, location);
		location.data().setModel(model);
	}
	
	@Override
	public void addModel(IModelList modelList, Module module) {
		modelList.add(DefaultKey.INSTANCE);
	}
}
