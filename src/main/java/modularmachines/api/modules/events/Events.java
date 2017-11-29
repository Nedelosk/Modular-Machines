package modularmachines.api.modules.events;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.components.IModuleComponent;
import modularmachines.api.modules.container.IModuleContainer;

public class Events extends Event {
	private final IModuleComponent component;
	
	private Events(IModuleComponent component) {
		this.component = component;
	}
	
	public IModuleComponent getComponent() {
		return component;
	}
	
	public static class EnergyChangeEvent extends Event {
		private final int energy;
		
		public EnergyChangeEvent(int energy) {
			this.energy = energy;
		}
		
		public int getEnergy() {
			return energy;
		}
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
	
	/**
	 * Fired after the container has loaded all data from the NBT.
	 */
	public static class ContainerLoadEvent extends Event {
		private final IModuleContainer container;
		
		public ContainerLoadEvent(IModuleContainer container) {
			this.container = container;
		}
		
		public IModuleContainer getContainer() {
			return container;
		}
	}
	
	/**
	 * Fired after the block that contains this module has been rotated.
	 */
	public static class FacingChangeEvent extends Event {
		private final IModuleContainer container;
		private final EnumFacing facing;
		
		public FacingChangeEvent(IModuleContainer container, EnumFacing facing) {
			this.container = container;
			this.facing = facing;
		}
		
		public EnumFacing getFacing() {
			return facing;
		}
		
		public IModuleContainer getContainer() {
			return container;
		}
	}
	
}
