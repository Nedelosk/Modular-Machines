package modularmachines.client.model.module;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IActivatableComponent;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModelList;

@SideOnly(Side.CLIENT)
public class BakeryActivatable extends BakeryBase {
	/**
	 * @param modelLocations 0 = inactive
	 *                       1 = active
	 */
	public BakeryActivatable(ResourceLocation... modelLocations) {
		super(modelLocations);
	}
	
	@Override
	public void bakeModel(IModule module, IModelInfo modelInfo, IModelList modelList) {
		boolean isActive = isActive(module);
		modelList.add(modelLocations.get(isActive ? 1 : 0));
	}
	
	private boolean isActive(IModule module) {
		IActivatableComponent component = module.getComponent(IActivatableComponent.class);
		if (component != null) {
			return component.isActive();
		}
		return false;
	}
}
