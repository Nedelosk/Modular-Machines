package modularmachines.api.modules.components;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;

import net.minecraftforge.items.IItemHandler;

import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;

public interface IItemHandlerComponent extends IItemHandler, IModuleComponent, INBTWritable, INBTReadable {
	
	default IItemSlot addSlot() {
		return addSlot(64);
	}
	
	default IItemSlot addSlot(int limit) {
		return addSlot(limit, false);
	}
	
	default IItemSlot addSlot(boolean isOutput) {
		return addSlot(64, false);
	}
	
	IItemSlot addSlot(int stackLimit, boolean isOutput);
	
	interface IItemSlot {
		IItemSlot setBackgroundTexture(String backgroundTexture);
		
		String getBackgroundTexture();
		
		int getIndex();
		
		int getLimit();
		
		IItemSlot setFilter(Predicate<ItemStack> filter);
		
		Predicate<ItemStack> getFilter();
		
		ItemStack getItem();
	}
}
