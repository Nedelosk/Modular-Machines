package de.nedelosk.forestmods.common.inventory;

import de.nedelosk.forestmods.common.inventory.slots.SlotAssemblerCasing;
import de.nedelosk.forestmods.common.inventory.slots.SlotAssemblerOutput;
import de.nedelosk.forestmods.library.inventory.ContainerBase;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerModularAssembler extends ContainerBase<IAssembler> {

	public ContainerModularAssembler(IAssembler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addInventory(InventoryPlayer inventory) {
		for(int i1 = 0; i1 < 3; i1++) {
			for(int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 48 + l1 * 18, 175 + i1 * 18));
			}
		}
		for(int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 48 + j1 * 18, 233));
		}
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotAssemblerCasing((IInventory) handler.getTile(), 0, 15, 15, inventory.player));
		addSlotToContainer(new SlotAssemblerOutput((IInventory) handler.getTile(), 1, 225, 15));
	}
}
