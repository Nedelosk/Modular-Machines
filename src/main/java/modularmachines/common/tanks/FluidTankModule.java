package modularmachines.common.tanks;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import modularmachines.api.modules.Module;
import modularmachines.common.inventory.IContentFilter;
import modularmachines.common.utils.IContentContainer;

public class FluidTankModule extends FluidTank implements IContentContainer<FluidStack> {
	
	protected final boolean isInput;
	protected final Module module;
	protected final int index;
	protected final List<IContentFilter<FluidStack, Module>> filters;
	
	public FluidTankModule(int capacity, int index, boolean isInput, Module module) {
		super(capacity);
		this.index = index;
		this.module = module;
		this.isInput = isInput;
		this.filters = new ArrayList<>();
	}
	
	public float getFilledRatio() {
		return (float) getFluidAmount() / getCapacity();
	}
	
	public boolean isFull() {
		return getFluidAmount() >= getCapacity();
	}
	
	public boolean isEmpty() {
		return getFluidAmount() <= 0;
	}
	
	@Override
	public FluidStack get() {
		return fluid;
	}
	
	@Override
	public void set(FluidStack content) {
		this.fluid = content;
	}
	
	@Override
	public boolean hasContent() {
		return fluid != null;
	}
	
	@Override
	public boolean isInput() {
		return isInput;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public Module getModule() {
		return module;
	}
	
	@Override
	public void markDirty() {
		module.sendModuleUpdate();
		module.getContainer().getLocatable().markLocatableDirty();
	}
	
	@Override
	protected void onContentsChanged() {
		markDirty();
	}
	
	@Override
	public FluidTankModule addFilter(IContentFilter<FluidStack, Module> filter) {
		filters.add(filter);
		return this;
	}
	
	@Override
	public List<IContentFilter<FluidStack, Module>> getFilters() {
		return filters;
	}
	
	@Override
	public boolean isValid(FluidStack content) {
		if (content == null) {
			return false;
		}
		if (filters.isEmpty()) {
			return !isInput;
		}
		for (IContentFilter<FluidStack, Module> filter : filters) {
			if (filter.isValid(index, content, module)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canDrain() {
		return !isInput;
	}
	
	@Override
	public boolean canFill() {
		return isInput;
	}
	
	@Override
	public boolean canDrainFluidType(FluidStack resource) {
		return super.canDrainFluidType(resource) && isValid(resource);
	}
	
	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return super.canFillFluidType(fluid) && isValid(fluid);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FluidTankModule)) {
			return false;
		}
		FluidTankModule tank = (FluidTankModule) obj;
		if (tank == this) {
			return true;
		}
		if (tank.fluid == null && fluid == null) {
			return true;
		}
		if (fluid == null) {
			return false;
		}
		return fluid.isFluidStackIdentical(tank.fluid);
	}
}
