package de.nedelosk.forestmods.common.modular.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.managers.IModularInventoryManager;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleDefault;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.basic.IModuleCategory;
import de.nedelosk.forestmods.api.modules.container.IInventoryContainer;
import de.nedelosk.forestmods.api.modules.container.IModuleContainer;
import de.nedelosk.forestmods.api.modules.container.IMultiInventoryContainer;
import de.nedelosk.forestmods.api.modules.container.IMultiModuleContainer;
import de.nedelosk.forestmods.api.modules.container.ISingleInventoryContainer;
import de.nedelosk.forestmods.api.modules.container.ISingleModuleContainer;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.inventory.ContainerModularMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class ModularInventoryManager implements IModularInventoryManager<IModularDefault> {

	private HashMap<String, IInventoryContainer> inventorys = Maps.newHashMap();
	private IModularDefault modular;
	private IModuleInventory currentInventory;

	public ModularInventoryManager() {
	}

	@Override
	public void addInventorys() {
		for ( Entry<String, IModuleContainer> entryContainer : (Set<Entry<String, IModuleContainer>>) modular.getModuleManager().getModuleContainers()
				.entrySet() ) {
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
			if (container instanceof ISingleInventoryContainer) {
				if (!(entryContainer.getValue() instanceof ISingleModuleContainer)) {
					return;
				}
				ModuleStack stack = ((ISingleModuleContainer) entryContainer.getValue()).getStack();
				if (stack.getModule() instanceof IModuleDefault && ((IModuleDefault) stack.getModule()).createInventory(stack) != null) {
					((ISingleInventoryContainer) container).setInventory(((IModuleDefault) stack.getModule()).createInventory(stack));
				}
			} else if (container instanceof IMultiInventoryContainer) {
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
			if (container instanceof ISingleInventoryContainer && ((ISingleInventoryContainer) container).getInventory() != null
					|| container instanceof IMultiInventoryContainer && !((IMultiInventoryContainer) container).getInventorys().isEmpty()) {
				container.setCategoryUID(entryContainer.getKey());
				inventorys.put(entryContainer.getKey(), container);
			}
		}
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		if (inventorys.isEmpty()) {
			addInventorys();
		}
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
	public IModuleInventory getInventory(String UID) {
		String[] UIDS = UID.split(":");
		IInventoryContainer container = getInventorys().get(UIDS[0]);
		if (container instanceof ISingleInventoryContainer) {
			ISingleInventoryContainer single = (ISingleInventoryContainer) container;
			if (single.getInventory() != null) {
				return single.getInventory();
			}
		} else if (container instanceof IMultiInventoryContainer) {
			IMultiInventoryContainer multi = (IMultiInventoryContainer) container;
			IModuleInventory gui = multi.getInventory(UIDS[1]);
			if (gui != null) {
				return gui;
			}
		}
		return currentInventory;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList listTag = nbt.getTagList("Inventorys", 10);
		for ( int i = 0; i < listTag.tagCount(); i++ ) {
			NBTTagCompound inventoryTag = listTag.getCompoundTagAt(i);
			String categoryUID = inventoryTag.getString("CategoryUID");
			IModuleCategory category = ModuleRegistry.getCategory(categoryUID);
			if (category == null) {
				continue;
			}
			Class<? extends IInventoryContainer> containerClass = category.getInventoryContainerClass();
			IInventoryContainer container;
			try {
				if (containerClass.isInterface()) {
					continue;
				}
				container = containerClass.newInstance();
			} catch (Exception e) {
				continue;
			}
			container.readFromNBT(inventoryTag, modular);
			inventorys.put(categoryUID, container);
		}
		String inventoryUID = nbt.getString("Inventory");
		if (inventoryUID == null || inventoryUID.equals("")) {
			currentInventory = getCasingInventory();
		} else {
			currentInventory = getInventory(inventoryUID);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList listTag = new NBTTagList();
		for ( Entry<String, IInventoryContainer> module : inventorys.entrySet() ) {
			NBTTagCompound inventoryTag = new NBTTagCompound();
			IInventoryContainer container = module.getValue();
			container.writeToNBT(inventoryTag, modular);
			inventoryTag.setString("CategoryUID", container.getCategoryUID());
			listTag.appendTag(inventoryTag);
		}
		nbt.setTag("Inventorys", listTag);
		if (currentInventory == null) {
			currentInventory = getCasingInventory();
		}
		nbt.setString("Inventory", currentInventory.getUID());
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
	public IModularDefault getModular() {
		return modular;
	}

	@Override
	public void setModular(IModularDefault modular) {
		this.modular = modular;
	}

	@Override
	public <T extends TileEntity & IModularTileEntity> Container getContainer(T tile, InventoryPlayer inventory) {
		if (currentInventory == null) {
			currentInventory = getCasingInventory();
		}
		return new ContainerModularMachine(tile, inventory, currentInventory);
	}

	private IModuleInventory getCasingInventory() {
		if (inventorys.isEmpty()) {
			addInventorys();
		}
		ISingleInventoryContainer container = (ISingleInventoryContainer) getInventorys().get(ModuleCategoryUIDs.CASING);
		if (container == null) {
			return null;
		}
		return container.getInventory();
	}

	@Override
	public HashMap<String, IInventoryContainer> getInventorys() {
		return inventorys;
	}

	@Override
	public IModuleInventory getCurrentInventory() {
		if (inventorys.isEmpty()) {
			addInventorys();
		}
		return currentInventory;
	}

	@Override
	public void setCurrentInventory(IModuleInventory currentInventory) {
		this.currentInventory = currentInventory;
	}
}
