package de.nedelosk.forestmods.api.modular.managers;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IModularInventoryManager<M extends IModular> extends IModularManager<M> {

	IModuleInventory getCurrentInventory();

	void setCurrentInventory(IModuleInventory inventory);

	IModuleInventory getInventory(ModuleStack stack);

	IModuleInventory getInventory(ModuleUID UID);

	IModuleInventory getInventory(Class<? extends IModule> moduleClass);

	<T extends TileEntity & IModularTileEntity> Container getContainer(T tile, InventoryPlayer inventory);

	int getSizeInventory(ModuleStack moduleStack);

	ItemStack getStackInSlot(int index, ModuleStack moduleStack);

	ItemStack decrStackSize(int index, int stacksize, ModuleStack moduleStack);

	ItemStack getStackInSlotOnClosing(int index, ModuleStack moduleStack);

	void setInventorySlotContents(int index, ItemStack itemStack, ModuleStack moduleStack);

	String getInventoryName(ModuleStack moduleStack);

	boolean hasCustomInventoryName(ModuleStack moduleStack);

	int getInventoryStackLimit(ModuleStack moduleStack);

	void markDirty(ModuleStack moduleStack);

	boolean isUseableByPlayer(EntityPlayer player, ModuleStack moduleStack);

	void openInventory(ModuleStack moduleStack);

	void closeInventory(ModuleStack moduleStack);

	boolean isItemValidForSlot(int index, ItemStack itemStack, ModuleStack moduleStack);

	int[] getAccessibleSlotsFromSide(int side, ModuleStack moduleStack);

	boolean canInsertItem(int index, ItemStack itemStack, int side, ModuleStack moduleStack);

	boolean canExtractItem(int index, ItemStack itemStack, int side, ModuleStack moduleStack);
}
