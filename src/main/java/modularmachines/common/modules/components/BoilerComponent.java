package modularmachines.common.modules.components;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.energy.IHeatSource;
import modularmachines.api.modules.energy.IHeatStep;
import modularmachines.common.energy.HeatManager;
import modularmachines.common.modules.components.energy.ProcessComponent;

public class BoilerComponent extends ProcessComponent {
	public static int waterPerWork = 100;
	public static int processLength = 40;
	
	@Override
	public boolean canWork() {
		IModuleContainer container = provider.getContainer();
		IHeatSource heatSource = container.getComponent(IHeatSource.class);
		if (heatSource == null || heatSource.getHeatStored() < HeatManager.BOILING_POINT) {
			return false;
		}
		IFluidHandler handler = container.getComponent(IFluidHandler.class);
		if (handler == null) {
			return false;
		}
		int waterCost = getWaterCost();
		FluidStack fluidStack = handler.drain(new FluidStack(FluidRegistry.WATER, waterCost), false);
		if (fluidStack == null || fluidStack.amount < waterCost) {
			return false;
		}
		return super.canWork();
	}
	
	@Override
	public boolean canProgress() {
		IModuleContainer container = provider.getContainer();
		IHeatSource heatSource = container.getComponent(IHeatSource.class);
		if (heatSource == null || heatSource.getHeatStored() < HeatManager.BOILING_POINT) {
			return false;
		}
		IFluidHandler handler = container.getComponent(IFluidHandler.class);
		if (handler == null) {
			return false;
		}
		FluidStack steam = getSteamAmount();
		if (handler.fill(steam, false) < steam.amount) {
			return false;
		}
		return super.canProgress();
	}
	
	@Override
	protected void onStartTask() {
		super.onStartTask();
	}
	
	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		IFluidHandler handler = provider.getContainer().getComponent(IFluidHandler.class);
		if (handler == null) {
			return;
		}
		handler.drain(new FluidStack(FluidRegistry.WATER, getWaterCost()), true);
		handler.fill(getSteamAmount(), true);
	}
	
	@Override
	public int getProcessLength() {
		return processLength;
	}
	
	private FluidStack getSteamAmount() {
		return new FluidStack(FluidRegistry.getFluid("steam"), getWaterCost() * HeatManager.STEAM_PER_UNIT_WATER);
	}
	
	private int getWaterCost() {
		IHeatSource heatSource = provider.getContainer().getComponent(IHeatSource.class);
		if (heatSource == null) {
			return 0;
		}
		IHeatStep heatLevel = heatSource.getHeatStep();
		return (heatLevel.getIndex() - 1) * waterPerWork;
	}
}
