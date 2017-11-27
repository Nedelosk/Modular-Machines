package modularmachines.common.modules.components.process.criteria;

import modularmachines.api.modules.components.process.IProcessComponent;
import modularmachines.api.modules.container.IHeatSource;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.events.Events;

public class HeatCriterion extends AbstractProcessCriterion {
	private final double neededHeat;
	
	public HeatCriterion(IProcessComponent component, double neededHeat) {
		super(component);
		this.neededHeat = neededHeat;
	}
	
	@Override
	protected void registerListeners(IModuleContainer container) {
		container.registerListener(Events.HeatChangeEvent.class, e -> markDirty());
	}
	
	@Override
	public void updateState() {
		IHeatSource heatSource = component.getProvider().getContainer().getComponent(IHeatSource.class);
		setState(heatSource != null && heatSource.getHeat() > neededHeat);
	}
}
