package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.modularmachines.common.inventory.slots.SlotAirHeatingPlant;
import nedelosk.modularmachines.common.multiblock.cowper.TileCowperAccessPort;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCowperAccessPort extends ContainerBase<TileCowperAccessPort> {

	public ContainerCowperAccessPort(TileCowperAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotAirHeatingPlant(inventoryBase, 0, 53, 35));
	}

}
