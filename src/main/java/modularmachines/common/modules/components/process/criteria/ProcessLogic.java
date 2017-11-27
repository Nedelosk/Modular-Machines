package modularmachines.common.modules.components.process.criteria;

import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.components.process.IProcessComponent;
import modularmachines.api.modules.components.process.IProcessCriterion;

public class ProcessLogic {
	private final Set<IProcessCriterion> criteria = new HashSet<>();
	private final IProcessComponent component;
	
	public ProcessLogic(IProcessComponent component) {
		this.component = component;
	}
	
	public void addHeat(double neededHeat) {
		addCriterion(new HeatCriterion(component, neededHeat));
	}
	
	public void addFluidDrain(Fluid fluid, boolean internal) {
		addCriterion(new DrainCriterion(component, fluid, internal));
	}
	
	public void addFluidDrain(FluidStack fluidStack, boolean internal) {
		addCriterion(new DrainCriterion(component, fluidStack, internal));
	}
	
	public void addFluidDrain(Fluid fluid) {
		addCriterion(new DrainCriterion(component, fluid, false));
	}
	
	public void addFluidFill(Fluid fluid, boolean internal) {
		addCriterion(new FillCriterion(component, fluid, internal));
	}
	
	public void addFluidFill(FluidStack fluidStack, boolean internal) {
		addCriterion(new FillCriterion(component, fluidStack, internal));
	}
	
	public void addFluidFill(Fluid fluid) {
		addCriterion(new FillCriterion(component, fluid, false));
	}
	
	public void addCriterion(IProcessCriterion criterion) {
		criteria.add(criterion);
	}
	
	public boolean canProgress() {
		for (IProcessCriterion criterion : criteria) {
			if (criterion.isDirty()) {
				criterion.updateState();
				criterion.setDirty(false);
			}
			if (!criterion.getState()) {
				return false;
			}
		}
		return true;
	}
}
