package modularmachines.common.modules.components;

import javax.annotation.Nullable;
import java.util.function.Predicate;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import modularmachines.api.modules.components.IFluidHandlerComponent;
import modularmachines.common.modules.ModuleComponent;

public class FluidHandlerComponent extends ModuleComponent implements IFluidHandlerComponent {
	private NonNullList<InternalTank> tanks = NonNullList.create();
	
	@Override
	public ITank addTank(int capacity, boolean isOutput) {
		InternalTank tank = new InternalTank(capacity, tanks.size(), isOutput);
		tanks.add(tank);
		return tank;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		IFluidTankProperties[] properties = new IFluidTankProperties[tanks.size()];
		for (InternalTank tank : tanks) {
			properties[tank.index] = tank.getTankProperties()[0];
		}
		return properties;
	}
	
	@Override
	public int fillInternal(FluidStack resource, boolean doFill) {
		return fill(resource, doFill, true);
	}
	
	@Override
	@Nullable
	public FluidStack drainInternal(FluidStack resource, boolean doDrain) {
		return drain(resource, doDrain, true);
	}
	
	@Override
	@Nullable
	public FluidStack drainInternal(int maxDrain, boolean doDrain) {
		return drain(maxDrain, doDrain, true);
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return fill(resource, doFill, false);
	}
	
	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return drain(resource, doDrain, false);
	}
	
	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return drain(maxDrain, doDrain, false);
	}
	
	private int fill(@Nullable FluidStack resource, boolean doFill, boolean internal) {
		if (resource == null || resource.amount <= 0) {
			return 0;
		}
		resource = resource.copy();
		int totalFillAmount = 0;
		for (InternalTank container : tanks) {
			if (!internal && container.canFillFluidType(resource)) {
				continue;
			}
			int fillAmount = container.fill(resource, doFill);
			totalFillAmount += fillAmount;
			resource.amount -= fillAmount;
			if (resource.amount <= 0) {
				break;
			}
		}
		return totalFillAmount;
	}
	
	@Nullable
	private FluidStack drain(@Nullable FluidStack resource, boolean doDrain, boolean internal) {
		if (resource == null || resource.amount <= 0) {
			return null;
		}
		resource = resource.copy();
		FluidStack totalDrained = null;
		for (InternalTank container : tanks) {
			if (!internal && container.canDrainFluidType(container.getFluid())) {
				continue;
			}
			FluidStack drain = container.drainInternal(resource, doDrain);
			if (drain != null) {
				if (totalDrained == null) {
					totalDrained = drain;
				} else {
					totalDrained.amount += drain.amount;
				}
				resource.amount -= drain.amount;
				if (resource.amount <= 0) {
					break;
				}
			}
		}
		return totalDrained;
	}
	
	@Nullable
	private FluidStack drain(int maxDrain, boolean doDrain, boolean internal) {
		if (maxDrain == 0) {
			return null;
		}
		FluidStack totalDrained = null;
		for (InternalTank container : tanks) {
			if (totalDrained == null) {
				if (!internal && container.canDrainFluidType(container.getFluid())) {
					continue;
				}
				totalDrained = container.drainInternal(maxDrain, doDrain);
				if (totalDrained != null) {
					maxDrain -= totalDrained.amount;
				}
			} else {
				FluidStack copy = totalDrained.copy();
				copy.amount = maxDrain;
				FluidStack drain = container.drain(copy, doDrain);
				if (drain != null) {
					totalDrained.amount += drain.amount;
					maxDrain -= drain.amount;
				}
			}
			if (maxDrain <= 0) {
				break;
			}
		}
		return totalDrained;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList nbtTagTankList = new NBTTagList();
		for (InternalTank container : tanks) {
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setInteger("Index", container.index);
			container.writeToNBT(tagCompound);
			nbtTagTankList.appendTag(tagCompound);
		}
		compound.setTag("Tanks", nbtTagTankList);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList tankList = compound.getTagList("Tanks", 10);
		for (int i = 0; i < tankList.tagCount(); i++) {
			NBTTagCompound tagCompound = tankList.getCompoundTagAt(i);
			int index = tagCompound.getInteger("Index");
			if (index >= tanks.size()) {
				continue;
			}
			InternalTank tank = tanks.get(index);
			tank.readFromNBT(tagCompound);
		}
	}
	
	public static class InternalTank extends FluidTank implements ITank {
		private final int index;
		private final boolean isOutput;
		private Predicate<FluidStack> filter = f -> true;
		
		private InternalTank(int capacity, int index, boolean isOutput) {
			super(capacity);
			this.isOutput = isOutput;
			this.index = index;
		}
		
		@Override
		public int getIndex() {
			return index;
		}
		
		@Override
		public void setFilter(Predicate<FluidStack> filter) {
			this.filter = filter;
		}
		
		@Override
		public Predicate<FluidStack> getFilter() {
			return filter;
		}
		
		@Override
		public boolean canFill() {
			return isOutput;
		}
		
		@Override
		public boolean canFillFluidType(FluidStack fluid) {
			return !isOutput && filter.test(fluid);
		}
	}
}
