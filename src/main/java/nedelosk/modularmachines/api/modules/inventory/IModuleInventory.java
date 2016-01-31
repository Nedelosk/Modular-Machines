package nedelosk.modularmachines.api.modules.inventory;

import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleInventory<M extends IModule<S>, S extends IModuleSaver> {

	List<Slot> addSlots(IContainerBase container, IModularInventory modular, ModuleStack<M, S> stack);

	ItemStack transferStackInSlot(ModuleStack<M, S> stack, IModularTileEntity tile, EntityPlayer player, int slotID, Container container);

	void readFromNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack<M, S> stack) throws Exception;

	void writeToNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack<M, S> stack) throws Exception;

	String getModuleUID();

	String getCategoryUID();

	int getSizeInventory(ModuleStack<M, S> moduleStack, IModularInventory modular);

	ItemStack getStackInSlot(int index, ModuleStack<M, S> moduleStack, IModularInventory modular);

	ItemStack decrStackSize(int index, int stacksize, ModuleStack<M, S> moduleStack, IModularInventory modular);

	ItemStack getStackInSlotOnClosing(int index, ModuleStack<M, S> moduleStack, IModularInventory modular);

	void setInventorySlotContents(int index, ItemStack itemStack, ModuleStack<M, S> moduleStack, IModularInventory modular);

	String getInventoryName(ModuleStack<M, S> moduleStack, IModularInventory modular);

	boolean hasCustomInventoryName(ModuleStack<M, S> moduleStack, IModularInventory modular);

	int getInventoryStackLimit(ModuleStack<M, S> moduleStack, IModularInventory modular);

	void markDirty(ModuleStack<M, S> moduleStack, IModularInventory modular);

	boolean isUseableByPlayer(EntityPlayer player, ModuleStack<M, S> moduleStack, IModularInventory modular);

	void openInventory(ModuleStack<M, S> moduleStack, IModularInventory modular);

	void closeInventory(ModuleStack<M, S> moduleStack, IModularInventory modular);

	boolean isItemValidForSlot(int index, ItemStack itemStack, ModuleStack<M, S> moduleStack, IModularInventory modular);

	int[] getAccessibleSlotsFromSide(int side, ModuleStack<M, S> moduleStack, IModularInventory modular);

	boolean canInsertItem(int index, ItemStack itemStack, int side, ModuleStack<M, S> moduleStack, IModularInventory modular);

	boolean canExtractItem(int index, ItemStack itemStack, int side, ModuleStack<M, S> moduleStack, IModularInventory modular);
}
