package modularmachines.common.modules.components;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.components.process.IProcessCriterion;
import modularmachines.api.modules.container.IHeatSource;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.events.Events;
import modularmachines.common.core.Fluids;
import modularmachines.common.energy.Heat;
import modularmachines.common.modules.components.process.ProcessComponent;
import modularmachines.common.modules.components.process.criteria.FakeProcessCriterion;
import modularmachines.common.modules.components.process.criteria.ProcessLogic;


@SuppressWarnings({"unchecked"})
public class BoilerComponent extends ProcessComponent {
	public static int waterPerWork = 100;
	public static int processLength = 40;
	
	private IProcessCriterion<FluidStack> drainCriterion = FakeProcessCriterion.INSTANCE;
	private IProcessCriterion<FluidStack> fillCriterion = FakeProcessCriterion.INSTANCE;
	
	@Override
	protected void addCriteria(ProcessLogic logic) {
		logic.addHeat(Heat.BOILING_POINT);
		drainCriterion = logic.addFluidDrain(FluidRegistry.WATER);
		FluidStack steamStack = Fluids.STEAM.get(Heat.STEAM_PER_UNIT_WATER);
		if (steamStack != null) {
			fillCriterion = logic.addFluidFill(steamStack, false);
		}
		IModuleContainer container = provider.getContainer();
		container.registerListener(Events.HeatChangeEvent.class, e -> {
			recalculateRequirements();
		});
		container.registerListener(Events.ContainerLoadEvent.class, e -> {
			recalculateRequirements();
		});
	}
	
	private void recalculateRequirements() {
		drainCriterion.setRequirement(new FluidStack(FluidRegistry.WATER, getWaterCost()));
		FluidStack steamStack = Fluids.STEAM.get((int) getSteamAmount());
		if (steamStack == null) {
			return;
		}
		fillCriterion.setRequirement(steamStack);
	}
	
	@Override
	public int getProcessLength() {
		return processLength;
	}
	
	private double getSteamAmount() {
		IHeatSource heatSource = provider.getContainer().getComponent(IHeatSource.class);
		if (heatSource == null) {
			return 0;
		}
		return getWaterCost() * Heat.STEAM_PER_UNIT_WATER * (heatSource.getHeat() / 100D);
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
