package nedelosk.modularmachines.api.modular.machines.manager;

import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface IModularInventoryManager extends INBTTagable {

    int getSizeInventory(String page);

    ItemStack getStackInSlot(String page, int slot);

    ItemStack decrStackSize(String page, int slot, int amount);

    ItemStack getStackInSlotOnClosing(String page, int slot);

    void setInventorySlotContents(String page, int slot, ItemStack p_70299_2_);

    int getInventoryStackLimit();
    
    void markDirty();

    void openInventory();

    void closeInventory();

    boolean isItemValidForSlot(String page, int slot, ItemStack stack);
    
    int[] getAccessibleSlotsFromSide(ForgeDirection side);

    boolean canInsertItem(int slot, ItemStack stack, ForgeDirection side);

    boolean canExtractItem(int slot, ItemStack stack, ForgeDirection side);
    
	boolean addToOutput(ItemStack output, int minSlot, int maxSlot, String page);
	
}