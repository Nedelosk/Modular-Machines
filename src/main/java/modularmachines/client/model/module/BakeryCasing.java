package modularmachines.client.model.module;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.positions.CasingPosition;
import modularmachines.common.core.Constants;

@SideOnly(Side.CLIENT)
public class BakeryCasing extends BakeryBase {
	
	public BakeryCasing(String casingFolder) {
		super(new ResourceLocation(Constants.MOD_ID, "module/casings/" + casingFolder + "/base"),
				new ResourceLocation(Constants.MOD_ID, "module/casings/" + casingFolder + "/side_left"),
				new ResourceLocation(Constants.MOD_ID, "module/casings/" + casingFolder + "/side_right"));
	}
	
	@Override
	public void bakeModel(IModule module, IModelInfo modelInfo, IModelList modelList) {
		modelList.add(modelLocations.get(0));
		
		IModuleProvider moduleProvider = module.getComponent(IModuleProvider.class);
		if (moduleProvider == null) {
			return;
		}
		IModuleHandler moduleHandler = moduleProvider.getHandler();
		bakeSides(moduleHandler, modelList);
	}
	
	private void bakeSides(IModuleHandler moduleHandler, IModelList modelList) {
		IModule left = moduleHandler.getModule(CasingPosition.LEFT);
		IModule right = moduleHandler.getModule(CasingPosition.RIGHT);
		
		if (left.isEmpty() || left.getData().isValidPosition(CasingPosition.FRONT)) {
			modelList.add(modelLocations.get(1));
		}
		if (right.isEmpty() || right.getData().isValidPosition(CasingPosition.FRONT)) {
			modelList.add(modelLocations.get(2));
		}
	}
}
