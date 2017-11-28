package modularmachines.common.modules.components;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.components.process.IProcessCriterion;
import modularmachines.api.modules.container.IHeatSource;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.events.Events;
import modularmachines.common.energy.HeatManager;
import modularmachines.common.modules.components.process.ProcessComponent;
import modularmachines.common.modules.components.process.criteria.FakeProcessCriterion;
import modularmachines.common.modules.components.process.criteria.ProcessLogic;


@SuppressWarnings("unchecked")
public class BoilerComponent extends ProcessComponent {
	public static int waterPerWork = 50;
	public static int processLength = 40;
	private IProcessCriterion<FluidStack> drainCriterion = FakeProcessCriterion.INSTANCE;
	private IProcessCriterion<FluidStack> fillCriterion = FakeProcessCriterion.INSTANCE;
	
	@Override
	protected void addCriteria(ProcessLogic logic) {
		logic.addHeat(HeatManager.BOILING_POINT);
		drainCriterion = logic.addFluidDrain(FluidRegistry.WATER);
		fillCriterion = logic.addFluidFill(new FluidStack(FluidRegistry.getFluid("steam"), HeatManager.STEAM_PER_UNIT_WATER), false);
		IModuleContainer container = provider.getContainer();
		container.registerListener(Events.HeatChangeEvent.class, e -> {
			recalculateRequirements();
		});
		container.registerListener(Events.ContainerLoadEvent.class, e -> {
			recalculateRequirements();
		});
	}
	
	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		/*IFluidHandler handler = provider.getContainer().getComponent(IFluidHandler.class);
		if (handler == null) {
			return;
		}
		handler.drain(new FluidStack(FluidRegistry.WATER, getWaterCost()), true);
		handler.fill(getSteamAmount(), true);*/
	}
	
	private void recalculateRequirements() {
		drainCriterion.setRequirement(new FluidStack(FluidRegistry.WATER, getWaterCost()));
		fillCriterion.setRequirement(getSteamAmount());
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
