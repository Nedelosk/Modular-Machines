package modularmachines.common.tanks;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import modularmachines.api.EnumIOMode;
import modularmachines.api.IIOConfigurable;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;

public class FluidHandlerWrapper implements IFluidHandler, IIOConfigurable {
	private final IFluidHandlerComponent[] subHandlers;
	@Nullable
	private final EnumFacing facing;
	
	public FluidHandlerWrapper(Collection<IFluidHandlerComponent> subHandlers, @Nullable EnumFacing facing) {
		this.subHandlers = subHandlers.toArray(new IFluidHandlerComponent[subHandlers.size()]);
		this.facing = facing;
	}
	
	@Override
	public boolean supportsMode(EnumIOMode ioMode, @Nullable EnumFacing facing) {
		return ioMode != EnumIOMode.DISABLED && getMode(facing) != EnumIOMode.DISABLED;
	}
	
	@Override
	public EnumIOMode getMode(@Nullable EnumFacing facing) {
		for (IIOConfigurable component : subHandlers) {
			if (component.getMode(facing) != EnumIOMode.DISABLED) {
				return EnumIOMode.NONE;
			}
		}
		return EnumIOMode.DISABLED;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		List<IFluidTankProperties> tanks = Lists.newArrayList();
		for (IFluidHandler handler : subHandlers) {
			Collections.addAll(tanks, handler.getTankProperties());
		}
		return tanks.toArray(new IFluidTankProperties[tanks.size()]);
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource == null || resource.amount <= 0) {
			return 0;
		}
		
		resource = resource.copy();
		
		int totalFillAmount = 0;
		for (IFluidHandlerComponent handler : subHandlers) {
			if (!handler.supportsMode(EnumIOMode.INPUT, facing)) {
				continue;
			}
			int fillAmount = handler.fill(resource, doFill);
			totalFillAmount += fillAmount;
			resource.amount -= fillAmount;
			if (resource.amount <= 0) {
				break;
			}
		}
		return totalFillAmount;
	}
	
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null || resource.amount <= 0) {
			return null;
		}
		
		resource = resource.copy();
		
		FluidStack totalDrained = null;
		for (IFluidHandlerComponent handler : subHandlers) {
			if (!handler.supportsMode(EnumIOMode.OUTPUT, facing)) {
				continue;
			}
			FluidStack drain = handler.drain(resource, doDrain);
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
	
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (maxDrain == 0) {
			return null;
		}
		FluidStack totalDrained = null;
		for (IFluidHandlerComponent handler : subHandlers) {
			if (!handler.supportsMode(EnumIOMode.OUTPUT, facing)) {
				continue;
			}
			if (totalDrained == null) {
				totalDrained = handler.drain(maxDrain, doDrain);
				if (totalDrained != null) {
					maxDrain -= totalDrained.amount;
				}
			} else {
				FluidStack copy = totalDrained.copy();
				copy.amount = maxDrain;
				FluidStack drain = handler.drain(copy, doDrain);
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
}
