package modularmachines.common.modules.transfer;

import java.util.function.Predicate;

import net.minecraft.nbt.NBTTagCompound;

public interface ITransferCycle<H> extends Comparable<ITransferCycle<H>> {
	
	int getComplexity();
	
	int getTime();
	
	int getPriority();
	
	int getAmount();
	
	void work(int ticks);
	
	boolean canWork();
	
	NBTTagCompound writeToNBT(NBTTagCompound compound);
	
	ITransferHandlerWrapper<H> getStartHandler();
	
	ITransferHandlerWrapper<H> getEndHandler();
	
	Predicate getFilter();
	
	@Override
	default int compareTo(ITransferCycle<H> o) {
		if (o.getPriority() > getPriority()) {
			return -1;
		} else if (o.getPriority() < getPriority()) {
			return 1;
		}
		return 0;
	}
}
