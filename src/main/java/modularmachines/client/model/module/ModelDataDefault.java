package modularmachines.client.model.module;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.model.DefaultProperty;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.common.core.Constants;

@SideOnly(Side.CLIENT)
public class ModelDataDefault extends ModelData {
	
	public static void addModelData(ModelLocationBuilder location) {
		ModelDataDefault model = new ModelDataDefault();
		model.add(DefaultProperty.INSTANCE, location);
		location.data().setModel(model);
	}
	
	public static void addModelData(IModuleData moduleData) {
		addModelData(moduleData, null);
	}
	
	public static void addModelData(IModuleData moduleData, @Nullable String fileName) {
		ModelDataDefault model = new ModelDataDefault();
		model.add(DefaultProperty.INSTANCE, new ResourceLocation(Constants.MOD_ID, "module/" + moduleData.getRegistryName().getResourcePath() + (fileName != null ? fileName : "")));
		moduleData.setModel(model);
	}
}
