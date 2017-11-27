package modularmachines.common.modules.components.process.criteria;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import modularmachines.api.modules.components.process.IProcessComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.events.Events;

public class FillCriterion extends AbstractProcessCriterion {
	private final FluidStack needed;
	private final boolean internal;
	
	public FillCriterion(IProcessComponent component, Fluid fluid, boolean internal) {
		this(component, new FluidStack(fluid, 1), internal);
	}
	
	public FillCriterion(IProcessComponent component, FluidStack needed, boolean internal) {
		super(component);
		this.needed = needed;
		this.internal = internal;
	}
	
	@Override
	protected void registerListeners(IModuleContainer container) {
		container.registerListener(Events.FluidChangeEvent.class, e -> markDirty());
	}
	
	@Override
	public void updateState() {
		IFluidHandler fluidHandler;
		if (internal) {
			fluidHandler = component.getProvider().getComponent(IFluidHandler.class);
		} else {
			fluidHandler = component.getProvider().getContainer().getComponent(IFluidHandler.class);
		}
		if (fluidHandler == null) {
			setState(false);
			return;
		}
		int drainedFluid = fluidHandler.fill(needed, false);
		setState(drainedFluid >= needed.amount);
	}
}
