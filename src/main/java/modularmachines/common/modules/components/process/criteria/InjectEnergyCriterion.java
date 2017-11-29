package modularmachines.common.modules.components.process.criteria;

import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.modules.components.process.IProcessComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.events.Events;
import modularmachines.common.utils.Log;

public class InjectEnergyCriterion extends AbstractProcessCriterion<Integer> {
	private final boolean internal;
	
	public InjectEnergyCriterion(IProcessComponent component, Integer requirement, boolean internal) {
		super(component, requirement);
		this.internal = internal;
	}
	
	@Override
	protected void registerListeners(IModuleContainer container) {
		container.registerListener(Events.EnergyChangeEvent.class, e -> markDirty());
	}
	
	@Override
	public void work() {
		IEnergyStorage energyStorage;
		if (internal) {
			energyStorage = component.getProvider().getComponent(IEnergyStorage.class);
		} else {
			energyStorage = component.getProvider().getContainer().getComponent(IEnergyStorage.class);
		}
		if (energyStorage == null) {
			Log.err("Failed to inject energy, because no energy storage exist.");
			return;
		}
		energyStorage.receiveEnergy(requirement, false);
	}
	
	@Override
	public void updateState() {
		IEnergyStorage energyStorage;
		if (internal) {
			energyStorage = component.getProvider().getComponent(IEnergyStorage.class);
		} else {
			energyStorage = component.getProvider().getContainer().getComponent(IEnergyStorage.class);
		}
		if (energyStorage == null) {
			setState(false);
			return;
		}
		int injectedAmount = energyStorage.receiveEnergy(requirement, true);
		setState(injectedAmount >= requirement);
	}
}
