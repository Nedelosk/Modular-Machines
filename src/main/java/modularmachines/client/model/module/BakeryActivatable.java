package modularmachines.client.model.module;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IActivatableComponent;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModelList;

public class BakeryActivatable extends BakeryBase {
	/**
	 * @param locations 0 = inactive
	 *                  1 = active
	 */
	public BakeryActivatable(String... locations) {
		super(locations);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void bakeModel(IModule module, IModelInfo modelInfo, IModelList modelList) {
		boolean isActive = isActive(module);
		modelList.add(modelLocations.get(isActive ? 1 : 0));
	}
	
	@SideOnly(Side.CLIENT)
	private boolean isActive(IModule module) {
		IActivatableComponent component = module.getComponent(IActivatableComponent.class);
		if (component != null) {
			return component.isActive();
		}
		return false;
	}
}
