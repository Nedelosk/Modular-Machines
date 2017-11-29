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
	
	public DrainCriterion addFluidDrain(Fluid fluid, boolean internal) {
		return addCriterion(new DrainCriterion(component, fluid, internal));
	}
	
	public DrainCriterion addFluidDrain(FluidStack fluidStack) {
		return addCriterion(new DrainCriterion(component, fluidStack, false));
	}
	
	public DrainCriterion addFluidDrain(FluidStack fluidStack, boolean internal) {
		return addCriterion(new DrainCriterion(component, fluidStack, internal));
	}
	
	public DrainCriterion addFluidDrain(Fluid fluid) {
		return addCriterion(new DrainCriterion(component, fluid, false));
	}
	
	public FillCriterion addFluidFill(Fluid fluid, boolean internal) {
		return addCriterion(new FillCriterion(component, fluid, internal));
	}
	
	public FillCriterion addFluidFill(FluidStack fluidStack) {
		return addCriterion(new FillCriterion(component, fluidStack, false));
	}
	
	public FillCriterion addFluidFill(FluidStack fluidStack, boolean internal) {
		return addCriterion(new FillCriterion(component, fluidStack, internal));
	}
	
	public FillCriterion addFluidFill(Fluid fluid) {
		return addCriterion(new FillCriterion(component, fluid, false));
	}
	
	public InjectEnergyCriterion addInjectEnergy(int energy) {
		return addCriterion(new InjectEnergyCriterion(component, energy, false));
	}
	
	public InjectEnergyCriterion addInjectEnergy(int energy, boolean internal) {
		return addCriterion(new InjectEnergyCriterion(component, energy, internal));
	}
	
	public <C extends IProcessCriterion> C addCriterion(C criterion) {
		criteria.add(criterion);
		return criterion;
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
	
	public void work() {
		for (IProcessCriterion criterion : criteria) {
			criterion.work();
			criterion.markDirty();
		}
	}
}
