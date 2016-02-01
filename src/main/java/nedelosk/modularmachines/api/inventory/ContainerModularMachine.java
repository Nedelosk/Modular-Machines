package nedelosk.modularmachines.api.inventory;

import java.util.List;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.forestcore.library.tile.TileBaseInventory;
import nedelosk.modularmachines.api.inventory.slots.SlotModularOutput;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerModularMachine<T extends TileBaseInventory & IModularTileEntity<IModularInventory>> extends ContainerBase<T> {

	public InventoryPlayer inventory;

	public ContainerModularMachine(T tileModularMachine, InventoryPlayer inventory) {
		super(tileModularMachine, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventoryPlayer) {
		this.inventory = inventoryPlayer;
		IModularGuiManager guiManager = inventoryBase.getModular().getGuiManager();
		ModuleStack stack = ModularUtils.getModuleStackFromGui(inventoryBase.getModular(), guiManager.getCurrentGui());
		IModuleInventory inventory = inventoryBase.getModular().getInventoryManager().getInventory(stack);
		if (inventory != null) {
			List<Slot> slots = inventory.addSlots(this, inventoryBase.getModular(), stack);
			if (slots != null && !slots.isEmpty()) {
				for ( Slot slot : slots ) {
					addSlotToContainer(slot);
				}
			}
		}
	}

	@Override
	protected void addInventory(InventoryPlayer inventoryPlayer) {
		IModularGuiManager guiManager = inventoryBase.getModular().getGuiManager();
		ModuleStack stack = ModularUtils.getModuleStackFromGui(inventoryBase.getModular(), guiManager.getCurrentGui());
		int i = guiManager.getCurrentGui().getGuiTop(inventoryBase.getModular(), stack) - 82;
		for ( int i1 = 0; i1 < 3; i1++ ) {
			for ( int l1 = 0; l1 < 9; l1++ ) {
				addSlotToContainer(new Slot(inventoryPlayer, l1 + i1 * 9 + 9, 8 + l1 * 18, i + i1 * 18));
			}
		}
		for ( int j1 = 0; j1 < 9; j1++ ) {
			addSlotToContainer(new Slot(inventoryPlayer, j1, 8 + j1 * 18, i + 58));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);
		ModuleStack<IModule, IModuleSaver> stack;
		IModularInventory modular = inventoryBase.getModular();
		try {
			stack = ModularUtils.getModuleStackFromGui(modular, modular.getGuiManager().getCurrentGui());
		} catch (Exception e) {
			stack = null;
		}
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slot instanceof SlotModularOutput) {
				if (!this.mergeItemStack(itemstack1, 0, 36, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (slot instanceof Slot) {
				if (stack != null && stack.getModule() != null) {
					IModuleInventory inventory = modular.getInventoryManager().getInventory(stack);
					return inventory.transferStackInSlot(stack, inventoryBase, player, slotID, this);
				} else if (slotID >= 0 && slotID < 27) {
					if (!this.mergeItemStack(itemstack1, 27, 36, false)) {
						return null;
					}
				} else if (slotID >= 27 && slotID < 36 && !this.mergeItemStack(itemstack1, 0, 27, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}
}
