package modularmachines.common.modules.filters;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.Module;
import modularmachines.common.inventory.IContentFilter;

public class FluidFilter implements IContentFilter<FluidStack, Module> {

	private static final Map<Fluid, FluidFilter> FILTERS = new HashMap<>();

	public static FluidFilter get(Fluid fluidFilter) {
		if (!FILTERS.containsKey(fluidFilter)) {
			FILTERS.put(fluidFilter, new FluidFilter(fluidFilter));
			return FILTERS.get(fluidFilter);
		}
		return FILTERS.get(fluidFilter);
	}

	private Fluid fluidFilter;

	private FluidFilter(Fluid fluidFilter) {
		this.fluidFilter = fluidFilter;
	}

	@Override
	public boolean isValid(int index, FluidStack content, Module module) {
		if (content == null) {
			return false;
		}
		return content.getFluid() == fluidFilter;
	}
}
