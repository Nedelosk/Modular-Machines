package modularmachines.client.model.module;

import javax.annotation.Nullable;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IActivatableComponent;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.model.DefaultProperty;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.common.core.Constants;

@SideOnly(Side.CLIENT)
public class ModelDataActivatable extends ModelData {
	
	public static void addModelData(ModelLocationBuilder locationOn, ModelLocationBuilder locationOff) {
		ModelDataActivatable working = new ModelDataActivatable();
		working.add(DefaultProperty.ACTIVE, locationOn);
		working.add(DefaultProperty.INACTIVE, locationOff);
		locationOn.data().setModel(working);
	}
	
	public static void addModelData(IModuleData moduleData) {
		ModelDataActivatable working = new ModelDataActivatable();
		working.add(DefaultProperty.ACTIVE, new ResourceLocation(Constants.MOD_ID, "module/" + moduleData.getRegistryName().getResourcePath() + "_on"));
		working.add(DefaultProperty.INACTIVE, new ResourceLocation(Constants.MOD_ID, "module/" + moduleData.getRegistryName().getResourcePath() + "_off"));
		moduleData.setModel(working);
	}
	
	@Override
	public IModuleModelState createState(IModule module) {
		boolean isActive = false;
		IActivatableComponent component = module.getComponent(IActivatableComponent.class);
		if (component != null) {
			isActive = component.isActive();
		}
		ModuleModelState modelState = new ModuleModelState();
		modelState.set(DefaultProperty.ACTIVE, isActive);
		return modelState;
	}
	
	@Override
	public void addModel(IModelList modelList, IModule module, IModuleModelState modelState, @Nullable BlockRenderLayer layer) {
		if (modelState.get(DefaultProperty.ACTIVE)) {
			modelList.add(DefaultProperty.ACTIVE);
		} else {
			modelList.add(DefaultProperty.INACTIVE);
		}
	}
}
