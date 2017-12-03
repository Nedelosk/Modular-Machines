package modularmachines.common.modules.components.handlers;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Predicate;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import modularmachines.api.IOMode;
import modularmachines.api.components.INetworkComponent;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.components.handlers.IIOComponent;
import modularmachines.api.modules.events.Events;
import modularmachines.common.utils.ModuleUtil;

public class FluidHandlerComponent extends HandlerComponent implements IFluidHandlerComponent {
	private final NonNullList<InternalTank> tanks = NonNullList.create();
	
	@Override
	public ITank addTank(int capacity, boolean isOutput) {
		InternalTank tank = new InternalTank(capacity, tanks.size(), isOutput);
		tanks.add(tank);
		return tank;
	}
	
	@Override
	public void writeData(PacketBuffer data) {
		for (InternalTank tank : tanks) {
			tank.writeData(data);
		}
	}
	
	@Override
	public void readData(PacketBuffer data) throws IOException {
		for (InternalTank tank : tanks) {
			tank.readData(data);
		}
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
			if (!internal && !container.canFillFluidType(resource)) {
				continue;
			}
			int fillAmount = container.fill(resource, doFill);
			totalFillAmount += fillAmount;
			resource.amount -= fillAmount;
			if (resource.amount <= 0) {
				break;
			}
		}
		if (totalFillAmount > 0 && doFill) {
			ModuleUtil.markDirty(provider);
			ModuleUtil.markForModelUpdate(provider);
			provider.sendToClient();
			provider.getContainer().receiveEvent(new Events.FluidChangeEvent(this, new FluidStack(resource.getFluid(), totalFillAmount), false));
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
			if (!internal && !container.canDrainFluidType(container.getFluid())) {
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
		if (totalDrained != null && doDrain) {
			ModuleUtil.markDirty(provider);
			ModuleUtil.markForModelUpdate(provider);
			provider.sendToClient();
			provider.getContainer().receiveEvent(new Events.FluidChangeEvent(this, totalDrained.copy(), true));
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
				if (!internal && !container.canDrainFluidType(container.getFluid())) {
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
		if (totalDrained != null && doDrain) {
			ModuleUtil.markDirty(provider);
			ModuleUtil.markForModelUpdate(provider);
			provider.sendToClient();
			provider.getContainer().receiveEvent(new Events.FluidChangeEvent(this, totalDrained.copy(), true));
		}
		return totalDrained;
	}
	
	@Nullable
	@Override
	public ITank getTank(int index) {
		if (index >= tanks.size()) {
			return null;
		}
		return tanks.get(index);
	}
	
	@Override
	public boolean supportsMode(IOMode ioMode, @Nullable EnumFacing facing) {
		IIOComponent ioComponent = provider.getComponent(IIOComponent.class);
		if (ioComponent == null) {
			return true;
		}
		return ioComponent.supportsMode(ioMode, facing);
	}
	
	@Override
	public IOMode getMode(@Nullable EnumFacing facing) {
		IIOComponent ioComponent = provider.getComponent(IIOComponent.class);
		if (ioComponent == null) {
			return IOMode.NONE;
		}
		return ioComponent.getMode(facing);
	}
	
	@Override
	public void doPull(EnumFacing facing) {
		EnumFacing relativeFacing = facing.getOpposite();
		TileEntity tileEntity = ModuleUtil.getTile(provider.getContainer(), facing);
		if (tileEntity == null || !tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, relativeFacing)) {
			return;
		}
		IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, relativeFacing);
		if (fluidHandler == null) {
			return;
		}
		for (InternalTank tank : tanks) {
			FluidStack fluidStack = tank.getFluid();
			if (fluidStack == null) {
				for (IFluidTankProperties properties : fluidHandler.getTankProperties()) {
					FluidStack content = properties.getContents();
					if (content == null || !properties.canDrain() || !tank.canFillFluidType(content)) {
						continue;
					}
					fluidStack = content;
				}
				if (fluidStack == null) {
					continue;
				}
			}
			int fillAmount = tank.fill(fluidStack, false);
			if (fillAmount <= 0) {
				continue;
			}
			FluidStack drained = fluidHandler.drain(new FluidStack(fluidStack.getFluid(), fillAmount), false);
			if (drained == null || drained.amount <= 0) {
				continue;
			}
			int amount = tank.fill(fluidHandler.drain(drained.copy(), true), true);
			if (amount > 0) {
				provider.getContainer().receiveEvent(new Events.FluidChangeEvent(this, new FluidStack(drained.getFluid(), amount), false));
			}
			break;
		}
	}
	
	@Override
	public void doPush(EnumFacing facing) {
		EnumFacing relativeFacing = facing.getOpposite();
		TileEntity tileEntity = ModuleUtil.getTile(provider.getContainer(), facing);
		if (tileEntity == null || !tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, relativeFacing)) {
			return;
		}
		IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, relativeFacing);
		if (fluidHandler == null) {
			return;
		}
		for (InternalTank tank : tanks) {
			FluidStack fluidStack = tank.drain(Integer.MAX_VALUE, false);
			if (fluidStack == null || fluidStack.amount <= 0) {
				continue;
			}
			int filledAmount = Math.min(fluidStack.amount, fluidHandler.fill(fluidStack.copy(), false));
			if (filledAmount <= 0) {
				continue;
			}
			FluidStack drained = tank.drain(fluidHandler.fill(new FluidStack(fluidStack.getFluid(), filledAmount), true), true);
			if (drained != null) {
				provider.getContainer().receiveEvent(new Events.FluidChangeEvent(this, drained, true));
			}
			break;
		}
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
	
	public static class InternalTank extends FluidTank implements ITank, INetworkComponent {
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
		
		@Override
		public void writeData(PacketBuffer data) {
			writeFluidStack(data, getFluid());
		}
		
		@Override
		public void readData(PacketBuffer data) throws IOException {
			setFluid(readFluidStack(data));
		}
		
		private void writeFluidStack(PacketBuffer data, @Nullable FluidStack fluidStack) {
			if (fluidStack == null) {
				data.writeVarInt(-1);
			} else {
				data.writeVarInt(fluidStack.amount);
				data.writeString(fluidStack.getFluid().getName());
			}
		}
		
		@Nullable
		private FluidStack readFluidStack(PacketBuffer data) {
			int amount = data.readVarInt();
			if (amount > 0) {
				String fluidName = data.readString(1024);
				Fluid fluid = FluidRegistry.getFluid(fluidName);
				if (fluid == null) {
					return null;
				}
				
				return new FluidStack(fluid, amount);
			}
			return null;
		}
	}
}
