package de.nedelosk.forestmods.api.modules.handlers.inventory;

import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.handlers.IModuleContentHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public interface IModuleInventory extends IModuleContentHandler<ItemStack>, ISidedInventory {

	boolean isInput(int index);

	ItemStack transferStackInSlot(IModularTileEntity tile, EntityPlayer player, int slotID, Container container);

	boolean mergeItemStack(ItemStack stack, int minSlot, int maxSlot, boolean maxToMin, Container container);
}
