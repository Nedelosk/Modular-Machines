package nedelosk.modularmachines.common.inventory;

import nedelosk.modularmachines.api.basic.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.inventory.slots.SlotModule;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerModularAssemblerSlot extends ContainerSelectionSlot {

	public ModuleEntry entry;
	
	public ContainerModularAssemblerSlot(IInventory tile, InventoryPlayer inventory, ModuleEntry entry) {
		super(tile, inventory);
		this.entry = entry;
	}
	
	@Override
	protected void addSlot(InventoryPlayer inventory) {
		addSlotToContainer(new SlotModule(inventoryBase, entry.ID, 80, 35, entry, inventory));
	}

}
