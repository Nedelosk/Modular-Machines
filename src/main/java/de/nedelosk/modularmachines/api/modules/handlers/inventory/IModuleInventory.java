package de.nedelosk.modularmachines.api.modules.handlers.inventory;

import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.recipes.IRecipeInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IModuleInventory<M extends IModule> extends IModuleContentHandler<ItemStack, M>, IRecipeInventory, IItemHandlerModifiable {

	/**
	 * @return True is the slot with the index a input slot.
	 */
	boolean isInput(int index);

	boolean canInsertItem(int index, ItemStack stack);

	boolean canExtractItem(int index, ItemStack stack);

	ItemStack transferStackInSlot(IModularTileEntity tile, EntityPlayer player, int slotID, Container container);

	boolean mergeItemStack(ItemStack stack, int minSlot, int maxSlot, boolean maxToMin, Container container);

	/**
	 * @return True has the inventory a name.
	 */
	boolean hasCustomInventoryName();

	/**
	 * @return The name of the inventory.
	 */
	String getInventoryName();
}
