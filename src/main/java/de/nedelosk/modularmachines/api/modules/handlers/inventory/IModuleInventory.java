package de.nedelosk.modularmachines.api.modules.handlers.inventory;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IAdvancedModuleContentHandler;
import de.nedelosk.modularmachines.api.recipes.IRecipeInventory;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IModuleInventory<M extends IModule> extends IAdvancedModuleContentHandler<ItemStack, M>, IRecipeInventory, IItemHandlerModifiable {

	boolean canInsertItem(int index, ItemStack stack);

	boolean canExtractItem(int index, ItemStack stack);

	ItemStack extractItemInternal(int slot, int amount, boolean simulate);

	ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate);

	boolean mergeItemStack(ItemStack stack, int minSlot, int maxSlot, boolean maxToMin, Container container);

	@Override
	@Nonnull
	SlotInfo getInfo(int index);

	@Override
	@Nonnull
	SlotInfo[] getContentInfos();
}
