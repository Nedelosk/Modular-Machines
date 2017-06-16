package modularmachines.common.modules.transfer.fluid;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.capability.IFluidHandler;

import modularmachines.common.modules.transfer.ITransferHandlerWrapper;
import modularmachines.common.modules.transfer.TransferCycle;

public class FluidTransferCycle extends TransferCycle<IFluidHandler> {
	
	public FluidTransferCycle(ModuleTransferFluid moduleTransfer, ITransferHandlerWrapper<IFluidHandler> startHandler, ITransferHandlerWrapper<IFluidHandler> endHandler, int time, int priority, int amount) {
		super(moduleTransfer, startHandler, endHandler, time, priority, amount);
	}
	
	public FluidTransferCycle(ModuleTransferFluid moduleTransfer, NBTTagCompound compound) {
		super(moduleTransfer, compound);
	}

	@Override
	public void work(int ticks) {
		if (ticks % time == 0) {
			//ItemUtil.transferStacks(moduleTransfer, this);
		}
	}

	@Override
	public boolean canWork() {
		return true;
	}

	@Override
	public int getComplexity() {
		return 0;
	}

	@Override
	public Predicate<ItemStack> getFilter() {
		return null;
	}
}
