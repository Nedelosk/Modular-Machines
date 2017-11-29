package modularmachines.api.modules.components.handlers;

import javax.annotation.Nullable;
import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.items.IItemHandlerModifiable;

import modularmachines.api.IIOConfigurable;
import modularmachines.api.IOMode;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.IModuleComponent;

/**
 * This component can be used to add a item handler to the module.
 * <p>
 * {@link modularmachines.api.modules.components.IModuleComponentFactory#addItemHandler(IModule)} can be
 * used to add this component to a module.
 */
public interface IItemHandlerComponent extends IItemHandlerModifiable, IModuleComponent, INBTWritable, INBTReadable, IIOConfigurable {
	
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
	
	ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate);
	
	ItemStack extractItemInternal(int slot, int amount, boolean simulate);
	
	@Nullable
	IItemSlot getSlot(int index);
	
	boolean supportsMode(IOMode ioMode, @Nullable EnumFacing facing);
	
	interface IItemSlot {
		IItemSlot setBackgroundTexture(String backgroundTexture);
		
		@Nullable
		String getBackgroundTexture();
		
		int getIndex();
		
		int getLimit();
		
		IItemSlot setFilter(Predicate<ItemStack> filter);
		
		Predicate<ItemStack> getFilter();
		
		ItemStack getItem();
		
		boolean isOutput();
	}
}
