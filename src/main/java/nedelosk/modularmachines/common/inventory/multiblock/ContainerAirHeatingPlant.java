package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerAirHeatingPlant extends ContainerBase {

	public ContainerAirHeatingPlant(IInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}

}
