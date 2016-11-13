package modularmachines.common.inventory;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.handlers.inventory.slots.SlotModule;

public class ContainerModular extends BaseContainer<IModularHandler> {

	public InventoryPlayer inventory;
	public IModulePage currentPage;

	public ContainerModular(IModularHandler tileModularMachine, InventoryPlayer inventoryPlayer, IModulePage currentPage) {
		super(tileModularMachine, inventoryPlayer);
		this.inventory = inventoryPlayer;
		this.currentPage = currentPage;
		this.currentPage.setContainer(this);
		addInventory(inventoryPlayer);
		addSlots(inventoryPlayer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		currentPage.detectAndSendChanges();
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		if (currentPage != null) {
			currentPage.setContainer(null);
		}
	}

	@Override
	protected void addSlots(InventoryPlayer inventoryPlayer) {
		if (currentPage != null) {
			List<SlotModule> slots = Lists.newArrayList();
			if (currentPage.getInventory() != null) {
				currentPage.createSlots(this, slots);
				for (SlotModule slot : slots) {
					addSlotToContainer(slot);
				}
			}
		}
	}

	@Override
	protected void addInventory(InventoryPlayer inventoryPlayer) {
		if (currentPage != null && currentPage.getPlayerInvPosition() >= 0) {
			int invPosition = currentPage.getPlayerInvPosition() + 1;
			for (int i1 = 0; i1 < 3; i1++) {
				for (int l1 = 0; l1 < 9; l1++) {
					addSlotToContainer(new Slot(inventoryPlayer, l1 + i1 * 9 + 9, 8 + l1 * 18, invPosition + i1 * 18));
				}
			}
			for (int j1 = 0; j1 < 9; j1++) {
				addSlotToContainer(new Slot(inventoryPlayer, j1, 8 + j1 * 18, invPosition + 58));
			}
		}
	}
}
