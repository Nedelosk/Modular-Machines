package modularmachines.client.model.module;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.model.DefaultProperty;
import modularmachines.api.modules.model.ModelLocationBuilder;

@SideOnly(Side.CLIENT)
public class ModelDataModuleRack extends ModelData {
	
	public static void initModelData(ModelLocationBuilder basicLocation) {
		ModelDataModuleRack storage = new ModelDataModuleRack();
		storage.add(DefaultProperty.INSTANCE, basicLocation.copy().setPreFix("storage"));
		basicLocation.data().setModel(storage);
	}
}
