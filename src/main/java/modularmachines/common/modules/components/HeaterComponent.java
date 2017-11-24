package modularmachines.common.modules.components;

import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.modules.container.components.HeatComponent;

public class HeaterComponent extends TickableComponent {
	protected final double maxHeat;
	protected final int heatModifier;
	
	public HeaterComponent(double maxHeat, int heatModifier) {
		this.maxHeat = maxHeat;
		this.heatModifier = heatModifier;
	}
	
	@Override
	public void update() {
		super.update();
		IFuelComponent fuelComponent = provider.getInterface(IFuelComponent.class);
		IModuleContainer container = provider.getContainer();
		if (fuelComponent == null || !tickHelper.updateOnInterval(20)) {
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
