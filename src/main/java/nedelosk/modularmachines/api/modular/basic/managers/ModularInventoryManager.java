package nedelosk.modularmachines.api.modular.basic.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.basic.container.gui.IMultiGuiContainer;
import nedelosk.modularmachines.api.modular.basic.container.gui.ISingleGuiContainer;
import nedelosk.modularmachines.api.modular.basic.container.inventory.IInventoryContainer;
import nedelosk.modularmachines.api.modular.basic.container.inventory.IMultiInventoryContainer;
import nedelosk.modularmachines.api.modular.basic.container.inventory.ISingleInventoryContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IMultiModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleDefault;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularInventoryManager implements IModularInventoryManager<IModularInventory> {

	private HashMap<String, ItemStack[]> slots = Maps.newHashMap();
	private HashMap<String, IInventoryContainer> inventorys = Maps.newHashMap();
	private IModularInventory modular;

	public ModularInventoryManager() {
	}

	@Override
	public void setInventorys() {
		for ( Entry<String, IModuleContainer> entryContainer : modular.getModuleContainers().entrySet() ) {
			Class<? extends IInventoryContainer> containerClass = ModuleRegistry.getCategory(entryContainer.getKey()).getInventoryContainerClass();
			IInventoryContainer container;
			try {
				if (containerClass.isInterface()) {
					return;
				}
				container = containerClass.newInstance();
			} catch (Exception e) {
				return;
			}
			if (container instanceof ISingleGuiContainer) {
				if (!(entryContainer.getValue() instanceof ISingleModuleContainer)) {
					return;
				}
				ModuleStack stack = ((ISingleModuleContainer) entryContainer.getValue()).getStack();
				if (stack.getModule() instanceof IModuleDefault) {
					((ISingleInventoryContainer) container).setInventory(((IModuleDefault) stack.getModule()).createInventory(stack));
				}
			} else if (container instanceof IMultiGuiContainer) {
				if (!(entryContainer.getValue() instanceof IMultiModuleContainer)) {
					return;
				}
				IMultiModuleContainer<IModule, IModuleSaver, Collection<ModuleStack<IModule, IModuleSaver>>> moduleContainer = (IMultiModuleContainer<IModule, IModuleSaver, Collection<ModuleStack<IModule, IModuleSaver>>>) entryContainer
						.getValue();
				for ( ModuleStack stack : moduleContainer.getStacks() ) {
					int index = moduleContainer.getIndex(stack);
					if (stack.getModule() instanceof IModuleDefault) {
						((IMultiInventoryContainer) container).addInventory(index, ((IModuleDefault) stack.getModule()).createInventory(stack));
					}
				}
			}
			inventorys.put(entryContainer.getKey(), container);
		}
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		IModule module = stack.getModule();
		IInventoryContainer container = inventorys.get(module.getCategoryUID());
		IModuleInventory inventory = null;
		if (container instanceof ISingleInventoryContainer) {
			inventory = ((ISingleInventoryContainer) container).getInventory();
		} else if (container instanceof IMultiInventoryContainer) {
			inventory = ((IMultiInventoryContainer) container).getInventory(module.getUID());
		}
		return inventory;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
	}

	@Override
	public int getSizeInventory(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getSizeInventory(moduleStack, modular);
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getStackInSlot(index, moduleStack, modular);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int stacksize, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.decrStackSize(index, stacksize, moduleStack, modular);
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getStackInSlotOnClosing(index, moduleStack, modular);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			inventory.setInventorySlotContents(index, itemStack, moduleStack, modular);
		}
	}

	@Override
	public String getInventoryName(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getInventoryName(moduleStack, modular);
		}
		return null;
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.hasCustomInventoryName(moduleStack, modular);
		}
		return false;
	}

	@Override
	public int getInventoryStackLimit(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getInventoryStackLimit(moduleStack, modular);
		}
		return 0;
	}

	@Override
	public void markDirty(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			inventory.markDirty(moduleStack, modular);
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.isUseableByPlayer(player, moduleStack, modular);
		}
		return false;
	}

	@Override
	public void openInventory(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			inventory.openInventory(moduleStack, modular);
		}
	}

	@Override
	public void closeInventory(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			inventory.closeInventory(moduleStack, modular);
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.isItemValidForSlot(index, itemStack, moduleStack, modular);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getAccessibleSlotsFromSide(side, moduleStack, modular);
		}
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStack, int side, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.canInsertItem(index, itemStack, side, moduleStack, modular);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack itemStack, int side, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.canExtractItem(index, itemStack, side, moduleStack, modular);
		}
		return false;
	}

	@Override
	public boolean addToOutput(ItemStack output, int slotMin, int slotMax, ModuleStack moduleStack) {
		if (output == null) {
			return true;
		}
		for ( int i = slotMin; i < slotMax; i++ ) {
			ItemStack itemStack = getStackInSlot(i, moduleStack);
			if (itemStack == null) {
				setInventorySlotContents(i, output, moduleStack);
				return true;
			} else {
				if (itemStack.getItem() == output.getItem() && itemStack.getItemDamage() == output.getItemDamage()) {
					if (itemStack.stackSize < itemStack.getMaxStackSize()) {
						int avaiableSpaceOnStack = itemStack.getMaxStackSize() - itemStack.stackSize;
						if (avaiableSpaceOnStack >= output.stackSize) {
							itemStack.stackSize = itemStack.stackSize + output.stackSize;
							setInventorySlotContents(i, itemStack, moduleStack);
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public IModularInventory getModular() {
		return modular;
	}

	@Override
	public void setModular(IModularInventory modular) {
		this.modular = modular;
	}
}
