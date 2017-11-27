package modularmachines.api.modules.events;

import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.components.IModuleComponent;

public class Events extends Event {
	private final IModuleComponent component;
	
	private Events(IModuleComponent component) {
		this.component = component;
	}
	
	public IModuleComponent getComponent() {
		return component;
	}
	
	public static class FluidChangeEvent extends Events {
		private final FluidStack changedFluid;
		private final boolean drained;
		
		public FluidChangeEvent(IModuleComponent component, FluidStack changedFluid, boolean drained) {
			super(component);
			this.changedFluid = changedFluid;
			this.drained = drained;
		}
		
		public FluidStack getChangedFluid() {
			return changedFluid.copy();
		}
		
		public boolean isDrained() {
			return drained;
		}
	}
	
	public static class HeatChangeEvent extends Event {
		private final double heat;
		
		public HeatChangeEvent(double heat) {
			this.heat = heat;
		}
		
		public double getHeat() {
			return heat;
		}
	}
	
}
