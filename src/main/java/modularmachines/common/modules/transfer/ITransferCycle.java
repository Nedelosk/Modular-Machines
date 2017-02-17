package modularmachines.common.modules.transfer;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ITransferCycle<H> extends Comparable<ITransferCycle<H>> {

	int getComplexity();

	int getTime();

	void work(int ticks);

	boolean canWork();

	int getPriority();
	
	NBTTagCompound writeToNBT(NBTTagCompound compound);

	ITransferHandlerWrapper<H> getStartHandler();

	ITransferHandlerWrapper<H> getEndHandler();
	
	Predicate<ItemStack> getFilter();
	
	@Override
	public default int compareTo(ITransferCycle<H> o) {
		if(o.getPriority() > getPriority()){
			return -1;
		}else if(o.getPriority() < getPriority()){
			return 1;
		}
		return 0;
	}
}
