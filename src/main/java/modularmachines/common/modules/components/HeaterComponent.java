package modularmachines.common.modules.components;

import net.minecraft.util.ITickable;

import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.modules.container.components.HeatComponent;
import modularmachines.common.modules.container.components.UpdateComponent;
import modularmachines.common.utils.ModuleUtil;

public class HeaterComponent extends ModuleComponent implements ITickable {
	protected final double maxHeat;
	protected final int heatModifier;
	
	public HeaterComponent(double maxHeat, int heatModifier) {
		this.maxHeat = maxHeat;
		this.heatModifier = heatModifier;
	}
	
	@Override
	public void update() {
		FuelComponent fuelComponent = provider.getComponent(FuelComponent.class);
		IModuleContainer container = provider.getContainer();
		UpdateComponent updateComponent = ModuleUtil.getUpdate(container);
		if (fuelComponent == null || updateComponent == null || !updateComponent.updateOnInterval(20)) {
			return;
		}
		boolean needUpdate;
		if (fuelComponent.hasFuel()) {
			HeatComponent heatComponent = container.getComponent(HeatComponent.class);
			heatComponent.increaseHeat(maxHeat, heatModifier);
			fuelComponent.removeFuel();
			needUpdate = true;
		} else {
			needUpdate = fuelComponent.updateFuel();
		}
		
		if (needUpdate) {
			provider.sendToClient();
			container.sendToClient();
		}
	}
}
