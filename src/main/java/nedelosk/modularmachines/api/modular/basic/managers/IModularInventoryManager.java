package nedelosk.modularmachines.api.modular.basic.managers;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IModularInventoryManager<M extends IModularInventory> extends IModularManager<M> {

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

	boolean addToOutput(ItemStack output, int slotMin, int slotMax, ModuleStack moduleStack);

	void setInventorys();

	IModuleInventory getInventory(ModuleStack stack);
}
