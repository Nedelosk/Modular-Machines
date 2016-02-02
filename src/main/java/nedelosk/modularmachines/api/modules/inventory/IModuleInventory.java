package nedelosk.modularmachines.api.modules.inventory;

import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.basic.IModularDefault;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleInventory<M extends IModule, S extends IModuleSaver> {

	List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<M, S> stack);

	ItemStack transferStackInSlot(ModuleStack<M, S> stack, IModularTileEntity tile, EntityPlayer player, int slotID, Container container);

	void readFromNBT(NBTTagCompound nbt, IModularDefault modular, ModuleStack<M, S> stack);

	void writeToNBT(NBTTagCompound nbt, IModularDefault modular, ModuleStack<M, S> stack);

	String getModuleUID();

	String getCategoryUID();

	String getUID();

	int getSizeInventory(ModuleStack<M, S> moduleStack, IModularDefault modular);

	ItemStack getStackInSlot(int index, ModuleStack<M, S> moduleStack, IModularDefault modular);

	ItemStack decrStackSize(int index, int stacksize, ModuleStack<M, S> moduleStack, IModularDefault modular);

	ItemStack getStackInSlotOnClosing(int index, ModuleStack<M, S> moduleStack, IModularDefault modular);

	void setInventorySlotContents(int index, ItemStack itemStack, ModuleStack<M, S> moduleStack, IModularDefault modular);

	String getInventoryName(ModuleStack<M, S> moduleStack, IModularDefault modular);

	boolean hasCustomInventoryName(ModuleStack<M, S> moduleStack, IModularDefault modular);

	int getInventoryStackLimit(ModuleStack<M, S> moduleStack, IModularDefault modular);

	void markDirty(ModuleStack<M, S> moduleStack, IModularDefault modular);

	boolean isUseableByPlayer(EntityPlayer player, ModuleStack<M, S> moduleStack, IModularDefault modular);

	void openInventory(ModuleStack<M, S> moduleStack, IModularDefault modular);

	void closeInventory(ModuleStack<M, S> moduleStack, IModularDefault modular);

	boolean isItemValidForSlot(int index, ItemStack itemStack, ModuleStack<M, S> moduleStack, IModularDefault modular);

	int[] getAccessibleSlotsFromSide(int side, ModuleStack<M, S> moduleStack, IModularDefault modular);

	boolean canInsertItem(int index, ItemStack itemStack, int side, ModuleStack<M, S> moduleStack, IModularDefault modular);

	boolean canExtractItem(int index, ItemStack itemStack, int side, ModuleStack<M, S> moduleStack, IModularDefault modular);

	int getPlayerInventoryYPosition(IModularDefault modular, ModuleStack stack);
}
