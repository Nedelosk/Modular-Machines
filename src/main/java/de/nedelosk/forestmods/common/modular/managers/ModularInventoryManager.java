package de.nedelosk.forestmods.common.modular.managers;

import java.util.List;

import de.nedelosk.forestmods.api.modular.managers.DefaultModularManager;
import de.nedelosk.forestmods.api.modular.managers.IModularInventoryManager;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.IModuleAdvanced;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.inventory.ContainerModularMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ModularInventoryManager extends DefaultModularManager implements IModularInventoryManager {

	private IModuleInventory currentInventory;

	public ModularInventoryManager() {
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		return getInventory(stack.getModule().getUID());
	}

	@Override
	public IModuleInventory getInventory(ModuleUID UID) {
		ModuleStack stack = modular.getManager(IModularModuleManager.class).getModuleStack(UID);
		if (stack != null && stack.getType().getGui() != null) {
			return stack.getType().getInventory();
		}
		return getCasingInventory();
	}

	@Override
	public IModuleInventory getInventory(Class<? extends IModule> moduleClass) {
		ModuleContainer container = ((List<ModuleContainer>) modular.getManager(IModularModuleManager.class).getModuleSatcks(moduleClass)).get(0);
		if (container != null && container.getGui() != null) {
			return container.getInventory();
		}
		return getCasingInventory();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		String inventoryUID = nbt.getString("Inventory");
		if (inventoryUID == null || inventoryUID.equals("")) {
			currentInventory = getCasingInventory();
		} else {
			currentInventory = getInventory(new ModuleUID(inventoryUID));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (currentInventory == null) {
			currentInventory = getCasingInventory();
		}
		nbt.setString("Inventory", currentInventory.getUID().toString());
	}

	@Override
	public int getSizeInventory(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getSizeInventory(moduleStack);
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getStackInSlot(index, moduleStack);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int stacksize, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.decrStackSize(index, stacksize, moduleStack);
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getStackInSlotOnClosing(index, moduleStack);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			inventory.setInventorySlotContents(index, itemStack, moduleStack);
		}
	}

	@Override
	public String getInventoryName(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getInventoryName(moduleStack);
		}
		return null;
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.hasCustomInventoryName(moduleStack);
		}
		return false;
	}

	@Override
	public int getInventoryStackLimit(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getInventoryStackLimit(moduleStack);
		}
		return 0;
	}

	@Override
	public void markDirty(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			inventory.markDirty(moduleStack);
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.isUseableByPlayer(player, moduleStack);
		}
		return false;
	}

	@Override
	public void openInventory(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			inventory.openInventory(moduleStack);
		}
	}

	@Override
	public void closeInventory(ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			inventory.closeInventory(moduleStack);
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.isItemValidForSlot(index, itemStack, moduleStack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.getAccessibleSlotsFromSide(side, moduleStack);
		}
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStack, int side, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.canInsertItem(index, itemStack, side, moduleStack);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack itemStack, int side, ModuleStack moduleStack) {
		IModuleInventory inventory = getInventory(moduleStack);
		if (inventory != null) {
			return inventory.canExtractItem(index, itemStack, side, moduleStack);
		}
		return false;
	}

	@Override
	public <T extends TileEntity & IModularState> Container getContainer(T tile, InventoryPlayer inventory) {
		if (currentInventory == null) {
			currentInventory = getCasingInventory();
		}
		return new ContainerModularMachine(tile, inventory, currentInventory);
	}

	private IModuleInventory getCasingInventory() {
		return getInventory(ModuleCategoryUIDs.CASING);
	}

	@Override
	public IModuleInventory getCurrentInventory() {
		return currentInventory;
	}

	@Override
	public void setCurrentInventory(IModuleInventory currentInventory) {
		this.currentInventory = currentInventory;
	}

	@Override
	public void onModularAssembled() {
		for ( ModuleStack stack : (List<ModuleStack>) modular.getManager(IModularModuleManager.class).getModuleStacks() ) {
			if (stack.getModule() instanceof IModuleAdvanced) {
				stack.getType().initInventory(stack, modular);
			}
		}
	}
}
