package nedelosk.forestbotany.common.inventory;

import nedelosk.forestbotany.common.inventory.slots.SlotGender;
import nedelosk.forestbotany.common.inventory.slots.SlotOutput;
import nedelosk.forestbotany.common.inventory.slots.SlotSoil;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerInfuserChamber extends ContainerBase {

	public ContainerInfuserChamber(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}
	
	@Override
	protected void addSlots(InventoryPlayer inventory) {
		//Plant
		addSlotToContainer(new SlotGender(tile, 0 , 80, 36));
		
		//Soil
		addSlotToContainer(new SlotSoil(tile, 1 , 80, 58));
		
		//Fruit Slots
		addSlotToContainer(new SlotOutput(tile, 2, 53, 36));
		addSlotToContainer(new SlotOutput(tile, 3, 57, 13));
		addSlotToContainer(new SlotOutput(tile, 4, 80, 9));
		addSlotToContainer(new SlotOutput(tile, 5, 103, 13));
		addSlotToContainer(new SlotOutput(tile, 6, 107, 36));
		
	}

}
