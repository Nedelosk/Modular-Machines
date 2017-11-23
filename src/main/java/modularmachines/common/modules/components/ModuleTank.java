package modularmachines.common.modules.components;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import modularmachines.api.modules.IModule;
import modularmachines.common.inventory.IContentFilter;
import modularmachines.common.utils.IContentContainer;

public class ModuleTank extends FluidTank implements IContentContainer<FluidStack> {
	
	protected final boolean isInput;
	protected final IModule module;
	protected final int index;
	protected final List<IContentFilter<FluidStack, IModule>> filters;
	
	public ModuleTank(int capacity, int index, boolean isInput, IModule module) {
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
	
	@Nullable
	@Override
	public FluidStack get() {
		return fluid;
	}
	
	@Override
	public void set(@Nullable FluidStack content) {
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
	public IModule getModule() {
		return module;
	}
	
	@Override
	public void markDirty() {
		module.sendToClient();
		module.getContainer().getLocatable().markLocatableDirty();
	}
	
	@Override
	protected void onContentsChanged() {
		markDirty();
	}
	
	@Override
	public ModuleTank addFilter(IContentFilter<FluidStack, IModule> filter) {
		filters.add(filter);
		return this;
	}
	
	@Override
	public List<IContentFilter<FluidStack, IModule>> getFilters() {
		return filters;
	}
	
	@Override
	public boolean isValid(FluidStack content) {
		if (filters.isEmpty()) {
			return !isInput;
		}
		for (IContentFilter<FluidStack, IModule> filter : filters) {
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
	public boolean canDrainFluidType(@Nullable FluidStack resource) {
		return super.canDrainFluidType(resource) && resource != null && isValid(resource);
	}
	
	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return super.canFillFluidType(fluid) && isValid(fluid);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ModuleTank)) {
			return false;
		}
		ModuleTank tank = (ModuleTank) obj;
		return tank == this || fluid == null || tank.fluid == null || fluid.isFluidStackIdentical(tank.fluid);
	}
}
