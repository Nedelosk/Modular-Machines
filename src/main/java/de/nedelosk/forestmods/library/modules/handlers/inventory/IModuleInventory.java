package de.nedelosk.forestmods.library.modules.handlers.inventory;

import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.library.recipes.IRecipeInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public interface IModuleInventory<M extends IModule> extends IModuleContentHandler<ItemStack, M>, ISidedInventory, IRecipeInventory {

	boolean isInput(int index);

	ItemStack transferStackInSlot(IModularTileEntity tile, EntityPlayer player, int slotID, Container container);

	boolean mergeItemStack(ItemStack stack, int minSlot, int maxSlot, boolean maxToMin, Container container);
}
