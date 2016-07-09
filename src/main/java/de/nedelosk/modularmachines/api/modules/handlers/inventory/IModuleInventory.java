package de.nedelosk.modularmachines.api.modules.handlers.inventory;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.recipes.IRecipeInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IModuleInventory<M extends IModule> extends IModuleContentHandler<ItemStack, M>, IRecipeInventory, IItemHandlerModifiable {

	boolean canInsertItem(int index, ItemStack stack);

	boolean canExtractItem(int index, ItemStack stack);

	ItemStack extractItemInternal(int slot, int amount, boolean simulate);

	ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate);

	ItemStack transferStackInSlot(IModularHandler tile, EntityPlayer player, int slotID, Container container);

	boolean mergeItemStack(ItemStack stack, int minSlot, int maxSlot, boolean maxToMin, Container container);
}
