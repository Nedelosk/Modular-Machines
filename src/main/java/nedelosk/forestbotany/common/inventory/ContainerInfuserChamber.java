package nedelosk.forestbotany.common.inventory;

import nedelosk.forestbotany.common.inventory.slots.SlotGender;
import nedelosk.forestbotany.common.inventory.slots.SlotOutput;
import nedelosk.forestbotany.common.inventory.slots.SlotSoil;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerInfuserChamber extends ContainerBase {

	public ContainerInfuserChamber(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}
	
	@Override
	protected void addSlots(InventoryPlayer inventory) {
		//Plant
		addSlotToContainer(new SlotGender(inventoryBase, 0 , 80, 36));
		
		//Soil
		addSlotToContainer(new SlotSoil(inventoryBase, 1 , 80, 58));
		
		//Fruit Slots
		addSlotToContainer(new SlotOutput(inventoryBase, 2, 53, 36));
		addSlotToContainer(new SlotOutput(inventoryBase, 3, 57, 13));
		addSlotToContainer(new SlotOutput(inventoryBase, 4, 80, 9));
		addSlotToContainer(new SlotOutput(inventoryBase, 5, 103, 13));
		addSlotToContainer(new SlotOutput(inventoryBase, 6, 107, 36));
		
	}

}
