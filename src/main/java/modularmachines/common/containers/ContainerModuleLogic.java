package modularmachines.common.containers;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.common.utils.ModuleUtil;

public class ContainerModuleLogic extends BaseContainer<IModuleLogic> {

	public IModuleGuiLogic guiLogic;
	
	public InventoryPlayer inventory;
	public ModulePage currentPage;

	public ContainerModuleLogic(IModuleLogic moduleLogic, InventoryPlayer inventory) {
		super(moduleLogic, inventory);
		this.inventory = inventory;
		this.guiLogic = ModuleUtil.getGuiLogic(moduleLogic, player);
		this.currentPage = guiLogic.getCurrentPage();
		addInventory(inventory);
		addSlots(inventory);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		currentPage.detectAndSendChanges();
	}

	@Override
	protected void addSlots(InventoryPlayer inventoryPlayer) {
		if (currentPage != null) {
			List<Slot> slots = Lists.newArrayList();
			currentPage.createSlots(slots);
			for (Slot slot : slots) {
				addSlotToContainer(slot);
			}
		}
	}

	@Override
	protected void addInventory(InventoryPlayer inventoryPlayer) {
		if (currentPage != null) {
			if(currentPage.getPlayerInvPosition() >= 0){
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
	
	public IModuleGuiLogic getGuiLogic() {
		return guiLogic;
	}
}
