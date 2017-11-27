package modularmachines.common.modules.components;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import modularmachines.api.modules.container.IHeatSource;
import modularmachines.common.energy.HeatManager;
import modularmachines.common.modules.components.process.ProcessComponent;
import modularmachines.common.modules.components.process.criteria.ProcessLogic;

public class BoilerComponent extends ProcessComponent {
	public static int waterPerWork = 50;
	public static int processLength = 40;
	
	/*@Override
	public boolean canWork() {
		IModuleContainer container = provider.getContainer();
		IHeatSource heatSource = container.getComponent(IHeatSource.class);
		if (heatSource == null || heatSource.getHeat() < HeatManager.BOILING_POINT) {
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
		if (heatSource == null || heatSource.getHeat() < HeatManager.BOILING_POINT) {
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
	}*/
	
	@Override
	protected void addCriteria(ProcessLogic logic) {
		logic.addHeat(HeatManager.BOILING_POINT);
		logic.addFluidDrain(FluidRegistry.WATER);
		logic.addFluidFill(new FluidStack(FluidRegistry.getFluid("steam"), HeatManager.STEAM_PER_UNIT_WATER), false);
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
		double heatLevel = heatSource.getHeatLevel();
		return (int) ((heatLevel) * waterPerWork);
	}
}
