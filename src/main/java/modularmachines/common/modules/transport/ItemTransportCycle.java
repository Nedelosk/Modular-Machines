package modularmachines.common.modules.transport;

import modularmachines.api.modules.transport.ITransportCycle;
import modularmachines.api.modules.transport.ITransportHandlerWrapper;
import modularmachines.api.modules.transport.TransportCycle;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ItemTransportCycle extends TransportCycle<IItemHandler> implements ITransportCycle<IItemHandler> {

	public ItemTransportCycle(ITransportHandlerWrapper<IItemHandler> startHandler, int[] startSlots, ITransportHandlerWrapper<IItemHandler> endHandler, int[] endSlots, int time, int property, int amount) {
		super(startHandler, startSlots, endHandler, endSlots, time, property, amount);
	}

	@Override
	public void work(int ticks) {
		if (ticks % time == 0) {
			ItemStack[] startStacks = new ItemStack[startSlots.length];
			for(int i = 0; i < startSlots.length; i++) {
				startStacks[i] = startHandler.getHandler().extractItem(startSlots[i], amount, true);
			}
			for(int startIndex = 0; startIndex < startSlots.length; startIndex++) {
				ItemStack itemStack = startStacks[startIndex];
				if (itemStack != null && itemStack.stackSize > 0) {
					for(int endIndex = 0; endIndex < endSlots.length; endIndex++) {
						ItemStack latestSack = itemStack;
						if (itemStack != null && itemStack.stackSize > 0) {
							itemStack = endHandler.getHandler().insertItem(endSlots[endIndex], itemStack, true);
							if (latestSack != null && itemStack == null || itemStack != null && latestSack.stackSize > itemStack.stackSize) {
								startHandler.getHandler().extractItem(startSlots[startIndex], itemStack.stackSize - endHandler.getHandler().insertItem(endSlots[endIndex], itemStack, false).stackSize, false);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canWork() {
		return true;
	}

	@Override
	protected int[] generateDefaultSlots(IItemHandler handler) {
		int[] slots = new int[handler.getSlots()];
		for(int i = 0; i < slots.length; i++) {
			slots[i] = i;
		}
		return slots;
	}

	@Override
	public int getComplexity() {
		return 0;
	}
}
