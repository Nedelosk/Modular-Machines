package nedelosk.modularmachines.common.inventory;

import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.modularmachines.common.inventory.slots.SlotModule;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerModularAssemblerSlot extends ContainerBase {

	public ModuleEntry entry;
	
	public ContainerModularAssemblerSlot(IInventory tile, InventoryPlayer inventory, ModuleEntry entry) {
		super(tile, inventory);
		this.entry = entry;
		addSlot(inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
	
	protected void addSlot(InventoryPlayer inventory) {
		entry.setAssembler((TileModularAssenbler) this.inventoryBase);
		addSlotToContainer(new SlotModule(entry.assembler, entry.ID, 80, 35, entry.moduleName, entry.moduleName2, entry.getTier(), entry.page));
	}

}
