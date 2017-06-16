package modularmachines.common.modules.transfer;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TransferCycle<H> implements ITransferCycle<H> {

	protected final int time;
	protected final int priority;
	protected final ITransferHandlerWrapper<H> startHandler;
	protected final ITransferHandlerWrapper<H> endHandler;
	protected final ModuleTransfer<H> moduleTransfer;
	protected final int amount;

	public TransferCycle(ModuleTransfer<H> moduleTransfer, ITransferHandlerWrapper<H> startHandler, ITransferHandlerWrapper<H> endHandler, int time, int priority, int amount) {
		this.moduleTransfer = moduleTransfer;
		this.startHandler = startHandler;
		this.endHandler = endHandler;
		this.time = time;
		this.priority = priority;
		this.amount = amount;
	}

	public TransferCycle(ModuleTransfer<H> moduleTransfer, NBTTagCompound compound) {
		this.moduleTransfer = moduleTransfer;
		time = compound.getInteger("Time");
		priority = compound.getInteger("Priority");
		amount = compound.getInteger("Amount");
		startHandler = moduleTransfer.getWrapper(compound.getTag("Start"));
		endHandler = moduleTransfer.getWrapper(compound.getTag("End"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Time", time);
		compound.setInteger("Priority", priority);
		compound.setInteger("Amount", amount);
		compound.setTag("Start", moduleTransfer.writeWrapper(startHandler));
		compound.setTag("End", moduleTransfer.writeWrapper(endHandler));
		return compound;
	}
	
	public int getAmount() {
		return amount;
	}
	
	@Override
	public int getTime() {
		return time;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public ITransferHandlerWrapper<H> getStartHandler() {
		return startHandler;
	}

	@Override
	public ITransferHandlerWrapper<H> getEndHandler() {
		return endHandler;
	}
	
}
